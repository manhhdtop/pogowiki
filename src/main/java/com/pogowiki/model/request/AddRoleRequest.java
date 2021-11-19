package com.pogowiki.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddRoleRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    @Valid
    @NotEmpty
    private List<ActionRequest> listAction;

    public void validate() {
        code = code.trim();
        name = name.trim();
        if (StringUtils.isNotBlank(description)) {
            description = description.trim();
        }
        listAction.forEach(ActionRequest::validate);
    }
}
