package com.pogowiki.controller.admin;

import com.pogowiki.model.request.AddRoleUserRequest;
import com.pogowiki.model.request.AddUserRequest;
import com.pogowiki.model.request.ChangePasswordRequest;
import com.pogowiki.model.request.SearchUserRequest;
import com.pogowiki.model.request.UpdateUserRequest;
import com.pogowiki.model.response.ApiBaseResponse;
import com.pogowiki.service.UserService;
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
@RequestMapping("${admin-endpoint:/admin}/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getListUser(SearchUserRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.getListUser(request)));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveUser() {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.getActiveUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.getUserDetail(id)));
    }

    @PostMapping
    public ResponseEntity<?> addUser(AddUserRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.addUser(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.updateUser(id, request)));
    }

    @PostMapping("/{id}/add-role")
    public ResponseEntity<?> addRole(@PathVariable Long id, @RequestBody @Valid AddRoleUserRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.addRole(id, request)));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(userService.changePassword(request)));
    }
}
