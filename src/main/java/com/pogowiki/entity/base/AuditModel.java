package com.pogowiki.entity.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditModel extends BaseModel {
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Long createdDate;
    @CreatedBy
    private Long createdBy;
    @Column(name = "updated_date")
    @LastModifiedDate
    private Long updatedDate;
    @LastModifiedBy
    private Long updatedBy;
}
