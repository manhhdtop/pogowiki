package com.pogowiki.repository;

import com.pogowiki.entity.UserEntity;
import com.pogowiki.repository.base.BaseRepository;
import com.pogowiki.repository.customer.UserRepositoryCustomer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends UserRepositoryCustomer, BaseRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    List<UserEntity> findByStatus(Integer status);
}
