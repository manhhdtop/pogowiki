package com.pogowiki.repository.customer;

import com.pogowiki.entity.UserEntity;
import com.pogowiki.model.request.SearchUserRequest;
import org.springframework.data.domain.Page;

public interface UserRepositoryCustomer {
    Page<UserEntity> searchUser(SearchUserRequest request);
}
