package com.pogowiki.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

@Data
public class ActionRequest {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    private Integer status;

    public void validate() {
        code = code.trim();
        name = name.trim();
        if (StringUtils.isNotBlank(description)) {
            description = description.trim();
        }
        status = 1;
    }
}
