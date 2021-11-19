package com.pogowiki.service;

import com.pogowiki.model.request.AddRoleRequest;
import com.pogowiki.model.request.RoleRequest;
import com.pogowiki.model.request.UpdateRoleRequest;
import com.pogowiki.model.response.RoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Page<RoleResponse> getListRole(RoleRequest request);

    List<RoleResponse> getActiveRole();

    RoleResponse addRole(AddRoleRequest request);

    RoleResponse update(Long id, UpdateRoleRequest request);
}
