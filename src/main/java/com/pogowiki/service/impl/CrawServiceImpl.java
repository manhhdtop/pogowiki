package com.pogowiki.service.impl;

import com.pogowiki.entity.CrawEntity;
import com.pogowiki.exception.BadRequestException;
import com.pogowiki.model.request.CrawRequest;
import com.pogowiki.repository.CrawRepository;
import com.pogowiki.service.CrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CrawServiceImpl implements CrawService {
    @Autowired
    private CrawRepository crawRepository;

    @Override
    public Page<CrawEntity> getListCraw(CrawRequest request) throws Exception {
        if (true) {
            throw new Exception();
        }
        Pageable pageable = request.getPageable();
        if (StringUtils.isBlank(request.getAction())) {
            return crawRepository.findAll(pageable);
        }
        return crawRepository.findByAction(request.getAction(), pageable);
    }
}
