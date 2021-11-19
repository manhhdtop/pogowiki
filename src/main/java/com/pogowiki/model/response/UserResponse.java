package com.pogowiki.model.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Integer status;
    private List<RoleResponse> roles;
}
