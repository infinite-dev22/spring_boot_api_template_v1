package io.nomard.spring_boot_api_template_v1.services.implementations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.nomard.spring_boot_api_template_v1.entities.Product;
import io.nomard.spring_boot_api_template_v1.errors.NotFoundException;
import io.nomard.spring_boot_api_template_v1.repositories.ProductRepository;
import io.nomard.spring_boot_api_template_v1.responses.SpotyResponseImpl;
import io.nomard.spring_boot_api_template_v1.services.auth.AuthServiceImpl;
import io.nomard.spring_boot_api_template_v1.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private SpotyResponseImpl spotyResponseImpl;

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public List<Product> getAll(int pageNo, int pageSize) {
        //create page request object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize/*, Sort.by("createdAt").descending()*/);
        //pass it to repos
        Page<Product> page = productRepo.findAll(pageRequest);
        //page.hasContent(); -- to check pages are there or not
        return page.getContent();
    }

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public Product getById(Long id) throws NotFoundException {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException();
        }
        return product.get();
    }

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public List<Product> getByContains(String search) {
        return productRepo.searchAll(search.toLowerCase());
    }

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public List<Product> getWarning() {
        return productRepo.findAllByQuantityIsLessThanEqualStockAlert();
    }

    @Override
    public ResponseEntity<ObjectNode> save(Product product) {
        try {
            product.setCreatedBy(authService.authUser());
            product.setCreatedAt(new Date());
            productRepo.saveAndFlush(product);
            return spotyResponseImpl.created();
        } catch (Exception e) {
            return spotyResponseImpl.error(e);
        }
    }

    @Override
    @CacheEvict(value = "products", key = "#data.id")
    public ResponseEntity<ObjectNode> update(Product data) throws NotFoundException {
        var opt = productRepo.findById(data.getId());

        if (opt.isEmpty()) {
            throw new NotFoundException();
        }
        var product = opt.get();

        if (Objects.nonNull(data.getBarcodeType()) && !"".equalsIgnoreCase(data.getBarcodeType())) {
            product.setBarcodeType(data.getBarcodeType());
        }

        if (Objects.nonNull(data.getProductType()) && !"".equalsIgnoreCase(data.getProductType())) {
            product.setProductType(data.getProductType());
        }

        if (Objects.nonNull(data.getName()) && !"".equalsIgnoreCase(data.getName())) {
            product.setName(data.getName());
        }

        if (!Objects.equals(data.getQuantity(), 0)) {
            product.setQuantity(data.getQuantity());
        }

        if (!Objects.equals(data.getCost(), 0)) {
            product.setCost(data.getCost());
        }

        if (!Objects.equals(data.getPrice(), 0)) {
            product.setPrice(data.getPrice());
        }

        if (!Objects.equals(data.getDiscount(), 0)) {
            product.setDiscount(data.getDiscount());
        }

        if (!Objects.equals(data.getNetTax(), 0)) {
            product.setNetTax(data.getNetTax());
        }

        if (Objects.nonNull(data.getTaxType()) && !"".equalsIgnoreCase(data.getTaxType())) {
            product.setTaxType(data.getTaxType());
        }

        if (!Objects.equals(data.getStockAlert(), 0)) {
            product.setStockAlert(data.getStockAlert());
        }

        if (Objects.nonNull(data.getSerialNumber()) && !"".equalsIgnoreCase(data.getSerialNumber())) {
            product.setSerialNumber(data.getSerialNumber());
        }

        if (Objects.nonNull(data.getImage()) && !"".equalsIgnoreCase(data.getImage())) {
            product.setImage(data.getImage());
        }

        product.setUpdatedBy(authService.authUser());
        product.setUpdatedAt(new Date());

        try {
            productRepo.saveAndFlush(product);
            return spotyResponseImpl.ok();
        } catch (Exception e) {
            return spotyResponseImpl.error(e);
        }
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public ResponseEntity<ObjectNode> delete(Long id) {
        try {
            productRepo.deleteById(id);
            return spotyResponseImpl.ok();
        } catch (Exception e) {
            return spotyResponseImpl.error(e);
        }
    }
}
