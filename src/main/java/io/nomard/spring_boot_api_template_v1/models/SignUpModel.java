package io.nomard.spring_boot_api_template_v1.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpModel {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String password;
    private String password2;
}
