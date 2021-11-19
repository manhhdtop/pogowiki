package com.pogowiki.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Status {
    DEACTIVE(0),
    ACTIVE(1);

    private final int status;

    Status(int status) {
        this.status = status;
    }

    public static Status of(int status) {
        return Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
    }

    @JsonValue
    public int getStatus() {
        return status;
    }
}
