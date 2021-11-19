package com.pogowiki.utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtResponse {
    private String token;
    private Long expire;
}
