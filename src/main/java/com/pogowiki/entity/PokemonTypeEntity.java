package com.pogowiki.entity;

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
@Table(name = "pokemon_type")
@Where(clause = "deleted is null or deleted = 0")
public class PokemonTypeEntity extends AuditModel {
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "color")
    private String color;
    @Column(name = "description")
    private String description;
}
