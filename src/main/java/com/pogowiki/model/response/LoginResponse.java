package com.pogowiki.model.response;

import com.pogowiki.utils.JwtResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private UserResponse user;
    private String token;
    private Long expire;

    public static LoginResponse of(UserResponse user, JwtResponse jwt) {
        return LoginResponse.builder()
                .user(user)
                .token(jwt.getToken())
                .expire(jwt.getExpire())
                .build();
    }
}
