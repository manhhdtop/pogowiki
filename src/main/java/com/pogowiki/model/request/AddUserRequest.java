package com.pogowiki.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class AddUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private String password;
    @NotEmpty
    private Set<Long> roleIds;

    public void validate(String defaultPassword) {
        username = username.trim();
        name = name.trim();
        email = email.trim();
        if (StringUtils.isBlank(password)) {
            password = defaultPassword;
        } else {
            password = password.trim();
        }
    }
}
