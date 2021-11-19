package com.pogowiki.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuditorContext {
    private static final String defaultAudit = "SYSTEM";
    private static final Long defaultAuditId = 0L;
    private String name;
    private Long userId;
    private String username;

    public void setUser(UserPrincipal userDetails) {
        userId = userDetails.getId();
        username = userDetails.getUsername();
        name = userDetails.getName();
    }
}
