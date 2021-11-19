package com.pogowiki.config;

import com.pogowiki.constant.UserStatus;
import com.pogowiki.entity.UserEntity;
import com.pogowiki.repository.UserRepository;
import com.pogowiki.repository.base.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.pogowiki.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@Slf4j
public class JpaConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        String adminUsername = "admin";
        UserEntity user = userRepository.findByUsername(adminUsername);
        if (user == null) {
            user = new UserEntity();
            user.setName("Administrator");
            user.setUsername(adminUsername);
            user.setEmail("support@taskmanager.com");
            String encryptPassword = passwordEncoder.encode("123456a@");
            user.setPassword(encryptPassword);
            user.setStatus(UserStatus.ACTIVE.getStatus());
            userRepository.save(user);
        }
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
