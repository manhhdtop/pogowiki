package com.pogowiki.model.request;

import com.pogowiki.utils.PageUtils;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

@Data
public class PageableRequest {
    @Min(0)
    private Integer page = 0;
    @Min(5)
    private Integer size = 20;

    public Pageable getPageable() {
        return PageUtils.getPageable(page, size);
    }
}
