package com.pogowiki.entity;

import com.pogowiki.constant.PokemonGroup;
import com.pogowiki.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "pokemon")
@Where(clause = "deleted is null or deleted = 0")
public class PokemonEntity extends AuditModel {
    @Column(name = "_index", nullable = false, unique = true)
    private Integer index;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "image_shiny")
    private String imageShiny;
    @Column(name = "debut", columnDefinition = "int default 0")
    private Boolean debut;
    @Column(name = "shiny_debut", columnDefinition = "int default 0")
    private Boolean shinyDebut;
    @ManyToOne
    @JoinColumn(name = "type")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PokemonTypeEntity type;
    @Column(name = "generation")
    private Integer generation;
    @Column(name = "_group")
    private PokemonGroup group;
    @Column(name = "status")
    private Integer status;
}
