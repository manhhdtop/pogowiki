package com.pogowiki.service.impl;

import com.pogowiki.config.UserPrincipal;
import com.pogowiki.entity.UserEntity;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.exception.UnauthorizedException;
import com.pogowiki.repository.UserRepository;
import com.pogowiki.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UnauthorizedException();
        }
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUserId(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new BadRequestException(String.format("User with ID: %d not found", id));
        }
        return UserPrincipal.create(user);
    }
}
