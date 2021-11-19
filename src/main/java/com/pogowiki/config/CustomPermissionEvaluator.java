package com.pogowiki.config;

import com.pogowiki.model.response.ActionResponse;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetDomainObject.toString().toUpperCase(), permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        List<ActionResponse> actions = principal.getRoles().stream()
                .map(e -> new ArrayList<>(e.getActions())).flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        Map<String, ActionResponse> actionMap = actions.stream().collect(Collectors.toMap(ActionResponse::getCode, Function.identity()));

        String targetAction = targetType + "." + permission;
        return actionMap.containsKey(targetAction);
    }
}
