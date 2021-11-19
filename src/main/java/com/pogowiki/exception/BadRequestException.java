package com.pogowiki.exception;

import com.pogowiki.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1616402540777823135L;

    public BadRequestException(String message) {
        super(MessageUtils.getMessage(message));
    }

    public static BadRequestException build(String message) {
        return new BadRequestException(message);
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
