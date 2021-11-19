package com.pogowiki.entity;

import com.pogowiki.entity.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Table(name = "counter")
@Where(clause = "deleted is null or deleted = 0")
public class CounterEntity extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "attacker")
    private PokemonTypeEntity attacker;
    @ManyToOne
    @JoinColumn(name = "defender")
    private PokemonTypeEntity defender;
    @Column(name = "value")
    private Integer value;
}
