package com.pogowiki.repository;

import com.pogowiki.entity.ActionEntity;
import com.pogowiki.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends BaseRepository<ActionEntity, Long> {
    @Query("SELECT a FROM ActionEntity a WHERE a.code LIKE %:keyword% OR a.name LIKE %:keyword% ORDER BY a.createdDate DESC")
    Page<ActionEntity> findByKeyword(String keyword, Pageable pageable);

    List<ActionEntity> findByStatusOrderByCreatedDateDesc(Integer status);

    @Query("SELECT a FROM ActionEntity a, RoleEntity r WHERE r.id=:roleId ORDER BY a.createdDate DESC")
    List<ActionEntity> findByRole(Long roleId);

    List<ActionEntity> findAllByCodeIn(List<String> codes);
}
