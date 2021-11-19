package com.pogowiki.service.impl;

import com.pogowiki.entity.RoleEntity;
import com.pogowiki.entity.UserEntity;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.model.request.AddRoleUserRequest;
import com.pogowiki.model.request.AddUserRequest;
import com.pogowiki.model.request.ChangePasswordRequest;
import com.pogowiki.model.request.SearchUserRequest;
import com.pogowiki.model.request.UpdateUserRequest;
import com.pogowiki.model.response.UserResponse;
import com.pogowiki.repository.RoleRepository;
import com.pogowiki.repository.UserRepository;
import com.pogowiki.service.UserService;
import com.pogowiki.utils.AssertUtil;
import com.pogowiki.utils.MapperUtils;
import com.pogowiki.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private MapperUtils mapperUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Value(value = "${app.user.default_password:taskmanager2021}")
    private String defaultPassword;

    @Override
    public Page<UserResponse> getListUser(SearchUserRequest request) {
        Page<UserEntity> page = userRepository.searchUser(request);
        return mapperUtils.mapPage(page);
    }

    @Override
    public List<UserResponse> getActiveUser() {
        List<UserEntity> users = userRepository.findByStatus(1);
        return mapperUtils.mapList(users);
    }

    @Override
    public UserResponse getUserDetail(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> BadRequestException.build(MessageUtils.getMessage("user.not_exist", id)));
        return mapperUtils.map(user, UserResponse.class);
    }

    @Override
    public UserResponse addUser(AddUserRequest request) {
        request.validate(defaultPassword);
        UserEntity user = userRepository.findByUsername(request.getUsername());
        AssertUtil.isNull(user, "user.username.exist");
        user = mapperUtils.map(request, UserEntity.class);
        List<RoleEntity> roles = roleRepository.findAllById(request.getRoleIds());
        user.setRoles(roles);
        String encryptPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptPassword);
        user = userRepository.save(user);
        return mapperUtils.map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        request.validate();
        UserEntity user = userRepository.findById(id).orElseThrow(() -> BadRequestException.build(MessageUtils.getMessage("user.not_exist", id)));
        mapperUtils.map(request, user);
        List<RoleEntity> roles = roleRepository.findAllById(request.getRoleIds());
        user.setRoles(roles);
        user = userRepository.save(user);
        return mapperUtils.map(user, UserResponse.class);
    }

    @Override
    public UserResponse addRole(Long id, AddRoleUserRequest request) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> BadRequestException.build(MessageUtils.getMessage("user.not_exist", id)));
        List<RoleEntity> roleEntities = roleRepository.findAllById(request.getRoleIds());
        AssertUtil.notEmpty(roleEntities, "role.ids.not_exist");
        user.getRoles().addAll(roleEntities);
        userRepository.save(user);
        return mapperUtils.map(user, UserResponse.class);
    }

    @Override
    public UserResponse changePassword(ChangePasswordRequest request) {
        return null;
    }
}
