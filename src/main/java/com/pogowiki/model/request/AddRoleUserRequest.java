package com.pogowiki.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddRoleUserRequest {
    @NotEmpty
    private List<Long> roleIds;
}
