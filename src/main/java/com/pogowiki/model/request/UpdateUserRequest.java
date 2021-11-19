package com.pogowiki.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String password;
    private Integer status;
    @NotNull
    private List<Long> roleIds;

    public void validate() {
        name = name.trim();
        email = email.trim();
        if (StringUtils.isNotBlank(password)) {
            password = password.trim();
        }
    }
}
