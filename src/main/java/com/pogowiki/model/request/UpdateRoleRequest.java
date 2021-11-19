package com.pogowiki.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UpdateRoleRequest {
    @NotBlank
    private String name;
    private String description;
    private Integer status;
    @Valid
    @NotEmpty
    private List<ActionRequest> listAction;

    public void validate() {
        name = name.trim();
        if (StringUtils.isNotBlank(description)) {
            description = description.trim();
        }
        if (status == null) {
            status = 1;
        }
        listAction.forEach(ActionRequest::validate);
    }
}
