package com.pogowiki.config;

import com.pogowiki.exception.UnauthorizedException;
import com.pogowiki.service.CustomUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pogowiki.utils.MessageUtils.getMessage;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private AuditorContext auditorContext;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Value("${admin-endpoint:}")
    private String adminEndpoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ") || !request.getRequestURI().startsWith(adminEndpoint)) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        try {
            Jwt jwt = jwtDecoder.decode(token);
            OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefault();
            OAuth2TokenValidatorResult validatorResult = jwtValidator.validate(jwt);
            if (validatorResult.hasErrors()) {
                chain.doFilter(request, response);
                return;
            }

            // Get user identity and set it on the spring security context
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwt.getSubject());
            auditorContext.setUser((UserPrincipal) userDetails);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), getMessage("message.unauthorized"));
        }
    }

}