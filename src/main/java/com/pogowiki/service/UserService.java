package com.pogowiki.service;

import com.pogowiki.model.request.AddRoleUserRequest;
import com.pogowiki.model.request.AddUserRequest;
import com.pogowiki.model.request.ChangePasswordRequest;
import com.pogowiki.model.request.SearchUserRequest;
import com.pogowiki.model.request.UpdateUserRequest;
import com.pogowiki.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserResponse> getListUser(SearchUserRequest request);

    List<UserResponse> getActiveUser();

    UserResponse getUserDetail(Long id);

    UserResponse addUser(AddUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    UserResponse addRole(Long id, AddRoleUserRequest request);

    UserResponse changePassword(ChangePasswordRequest request);
}
