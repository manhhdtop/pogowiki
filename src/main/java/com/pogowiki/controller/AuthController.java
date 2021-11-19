package com.pogowiki.controller;

import com.pogowiki.config.UserPrincipal;
import com.pogowiki.constant.UserStatus;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.model.request.LoginRequest;
import com.pogowiki.model.response.LoginResponse;
import com.pogowiki.model.response.UserResponse;
import com.pogowiki.service.CustomUserDetailsService;
import com.pogowiki.utils.AssertUtil;
import com.pogowiki.utils.JwtHelper;
import com.pogowiki.utils.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String username = request.getUsername().toLowerCase().trim();
        String password = request.getPassword().toLowerCase().trim();
        UserPrincipal user;
        try {
            user = (UserPrincipal) customUserDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new BadRequestException("login.error");
        }
        AssertUtil.isTrue(passwordEncoder.matches(password, user.getPassword()), "login.error");
        if (user.getStatus().equals(UserStatus.DEACTIVE.getStatus())) {
            throw BadRequestException.build("login.user.status.not_active");
        }
        if (user.getStatus().equals(UserStatus.LOCK.getStatus())) {
            throw BadRequestException.build("login.user.status.locked");
        }
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        Map<String, String> claims = new HashMap<>();
        String authorities = user.getAuthoritiesString();
        claims.put("authorities", authorities);
        claims.put("userId", String.valueOf(user.getId()));

        JwtResponse jwt = jwtHelper.createJwtForClaims(username, claims);
        return ResponseEntity.ok(LoginResponse.of(userResponse, jwt));
    }
}
