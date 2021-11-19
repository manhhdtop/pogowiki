package com.pogowiki.repository;

import com.pogowiki.entity.CrawEntity;
import com.pogowiki.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrawRepository extends BaseRepository<CrawEntity, Long> {
    Page<CrawEntity> findByAction(String action, Pageable pageable);
}
