package com.pogowiki.repository;

import com.pogowiki.entity.RoleEntity;
import com.pogowiki.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends BaseRepository<RoleEntity, Long> {
    @Query("SELECT r FROM RoleEntity r WHERE r.code LIKE %:keyword% OR r.name LIKE %:keyword% ORDER BY r.createdDate DESC")
    Page<RoleEntity> findByKeyword(String keyword, Pageable pageable);

    List<RoleEntity> findByStatusOrderByCreatedDateDesc(Integer status);

    RoleEntity findByCode(String code);
}
