package com.pogowiki.controller.admin;

import com.pogowiki.model.request.AddRoleRequest;
import com.pogowiki.model.request.RoleRequest;
import com.pogowiki.model.request.UpdateRoleRequest;
import com.pogowiki.model.response.ApiBaseResponse;
import com.pogowiki.service.ActionService;
import com.pogowiki.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${admin-endpoint:/admin}/role")
@Slf4j
public class RoleController {
    @Autowired
    private ActionService actionService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getListRole(RoleRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(roleService.getListRole(request)));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveRole() {
        return ResponseEntity.ok(ApiBaseResponse.success(roleService.getActiveRole()));
    }

    @GetMapping("/{roleId}/action")
    public ResponseEntity<?> getByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(ApiBaseResponse.success(actionService.getActionByRole(roleId)));
    }

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody @Valid AddRoleRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(roleService.addRole(request)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UpdateRoleRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(roleService.update(id, request)));
    }
}
