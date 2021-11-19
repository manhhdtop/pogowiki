package com.pogowiki.exception;

import com.pogowiki.utils.MessageUtils;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(MessageUtils.getMessage(message), cause);
    }
}
