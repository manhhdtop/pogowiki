package com.pogowiki.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchUserRequest extends PageableRequest {
    private String username;
    private String name;
    private Integer status;
}
