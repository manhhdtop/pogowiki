package com.pogowiki.entity;

import com.pogowiki.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "role")
@Where(clause = "deleted is null or deleted = 0")
public class RoleEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_action", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "action_id"))
    private List<ActionEntity> actions;
}
