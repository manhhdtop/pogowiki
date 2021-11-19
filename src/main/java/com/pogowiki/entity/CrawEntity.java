package com.pogowiki.entity;

import com.pogowiki.constant.Status;
import com.pogowiki.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "craw")
@Where(clause = "deleted is null or deleted = 0")
public class CrawEntity extends AuditModel {
    @Column(name = "name")
    private String name;
    @Column(name = "action")
    private String action;
    @Column(name = "status")
    private Status status;
}
