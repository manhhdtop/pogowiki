package com.pogowiki.model.response;

import com.pogowiki.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiBaseResponse implements Serializable {
    public static String CODE_SUCCESS = "code.success";
    public static String CODE_BAD_REQUEST = "code.bad_request";
    private static String MESSAGE_SUCCESS = "message.success";
    private String errorCode;
    private String errorDescription;
    private Object data;
    private Object optional;

    public static ApiBaseResponse success() {
        return success(MessageUtils.getMessage(MESSAGE_SUCCESS), null);
    }

    public static ApiBaseResponse success(Object data) {
        return success(MessageUtils.getMessage(MESSAGE_SUCCESS), data);
    }

    public static ApiBaseResponse success(Object data, Object optional) {
        return success(MessageUtils.getMessage(MESSAGE_SUCCESS), data, optional);
    }

    public static ApiBaseResponse success(String message, Object data) {
        return success(message, data, null);
    }

    public static ApiBaseResponse success(String message, Object data, Object optional) {
        return ApiBaseResponse.builder()
                .errorCode(MessageUtils.getMessage(CODE_SUCCESS))
                .errorDescription(MessageUtils.getMessage(message))
                .data(data)
                .optional(optional)
                .build();
    }

    public static ApiBaseResponse error(String message) {
        return error(MessageUtils.getMessage(CODE_BAD_REQUEST), message);
    }

    public static ApiBaseResponse error(String errorCode, String message) {
        return ApiBaseResponse.builder()
                .errorCode(errorCode)
                .errorDescription(MessageUtils.getMessage(message))
                .build();
    }

    public static ApiBaseResponse error(String message, Object details) {
        return ApiBaseResponse.builder()
                .errorCode(MessageUtils.getMessage(CODE_BAD_REQUEST))
                .errorDescription(MessageUtils.getMessage(message))
                .data(details)
                .build();
    }
}
