package com.pogowiki.repository.customer.impl;

import com.pogowiki.entity.UserEntity;
import com.pogowiki.model.request.SearchUserRequest;
import com.pogowiki.repository.customer.UserRepositoryCustomer;
import com.pogowiki.utils.Constant;
import com.pogowiki.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryCustomerImpl implements UserRepositoryCustomer {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<UserEntity> searchUser(SearchUserRequest request) {
        Pageable pageable = request.getPageable();
        int count = countSearchUser(request);
        if (count <= 0) {
            return new PageUtils<UserEntity>().setPage(new ArrayList<>(), pageable, 0);
        }

        Map<String, Object> params = new HashMap<>();
        String sql = buildSearchUserSql(request, params, Constant.SqlType.LIST);
        Query query = entityManager.createNativeQuery(sql, UserEntity.class);
        if (!params.isEmpty()) {
            params.forEach(query::setParameter);
        }
        PageUtils.setPageable(query, pageable);
        return new PageUtils<UserEntity>().setPage(query.getResultList(), pageable, count);
    }

    private int countSearchUser(SearchUserRequest request) {
        Map<String, Object> params = new HashMap<>();
        String sql = buildSearchUserSql(request, params, Constant.SqlType.COUNTING);
        Query query = entityManager.createNativeQuery(sql);
        if (!params.isEmpty()) {
            params.forEach(query::setParameter);
        }
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String buildSearchUserSql(SearchUserRequest request, Map<String, Object> params, boolean counting) {
        StringBuilder sb = new StringBuilder();
        if (counting) {
            sb.append("SELECT COUNT(id) \n");
        } else {
            sb.append("SELECT * \n");
        }
        sb.append("FROM user u \n");
        sb.append("WHERE u.deleted=0 \n");
        if (StringUtils.isNotBlank(request.getUsername())) {
            sb.append("    AND u.username LIKE '%:username%' \n");
            params.put("username", request.getUsername().trim());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            sb.append("    AND u.name LIKE '%:name%' \n");
            params.put("name", request.getName().trim());
        }
        if (request.getStatus() != null) {
            sb.append("    AND u.status=:status \n");
            params.put("status", request.getStatus());
        }
        if (!counting) {
            sb.append("ORDER BY u.created_date DESC");
        }
        return sb.toString();
    }
}
