package com.pogowiki.exception;

import com.pogowiki.model.response.ApiBaseResponse;
import com.pogowiki.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("(handleAllExceptions) request: {}, Exception: {}", request, ex);
        return ResponseEntity.ok(ApiBaseResponse.error("500", "message.error"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public final void handleUnauthorizedException(AccessDeniedException ex, WebRequest request) {
        throw new AccessDeniedException();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public final void handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        throw UnauthorizedException.build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("(handleBadRequestException) request: {}, Exception: {}", request, ex);
        return ResponseEntity.ok(ApiBaseResponse.error("message.bad_request"));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public final ResponseEntity<?> handleSizeLimitExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        log.error("(handleSizeLimitExceededException) request: {}, Exception: {}", request, ex);
        return ResponseEntity.ok(ApiBaseResponse.error("file.limit_exceeded"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String message = error.getDefaultMessage();
            if (message == null) {
                message = "";
            }
            details.add(error.getField() + ": " + MessageUtils.getMessage(message.replaceAll("[{}]", "")));
        }
        ApiBaseResponse error = ApiBaseResponse.error(MessageUtils.getMessage("message.bad_request"), details);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        throw new UnauthorizedException();
    }
}