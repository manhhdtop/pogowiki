package com.pogowiki.service;

import com.pogowiki.entity.CrawEntity;
import com.pogowiki.model.request.CrawRequest;
import org.springframework.data.domain.Page;

public interface CrawService {
    Page<CrawEntity> getListCraw(CrawRequest request) throws Exception;
}
