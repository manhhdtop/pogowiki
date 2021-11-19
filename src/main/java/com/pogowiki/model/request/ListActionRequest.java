package com.pogowiki.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListActionRequest extends PageableRequest {
    private String keyword;
}
