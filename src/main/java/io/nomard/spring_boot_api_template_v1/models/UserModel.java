/*
 * Copyright (c) 2023, Jonathan Mark Mwigo. All rights reserved.
 *
 * The computer system code contained in this file is the property of Jonathan Mark Mwigo and is protected by copyright law. Any unauthorized use of this code is prohibited.
 *
 * This copyright notice applies to all parts of the computer system code, including the source code, object code, and any other related materials.
 *
 * The computer system code may not be modified, translated, or reverse-engineered without the express written permission of Jonathan Mark Mwigo.
 *
 * Jonathan Mark Mwigo reserves the right to update, modify, or discontinue the computer system code at any time.
 *
 * Jonathan Mark Mwigo makes no warranties, express or implied, with respect to the computer system code. Jonathan Mark Mwigo shall not be liable for any damages, including, but not limited to, direct, indirect, incidental, special, consequential, or punitive damages, arising out of or in connection with the use of the computer system code.
 */

package io.nomard.spring_boot_api_template_v1.models;

import io.nomard.spring_boot_api_template_v1.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String phone;
    private Set<Role> roles;
    @Builder.Default
    private boolean active = true;
    @Builder.Default
    private boolean locked = false;
    @Builder.Default
    private boolean accessAllBranches = false;
    private String avatar;
}
