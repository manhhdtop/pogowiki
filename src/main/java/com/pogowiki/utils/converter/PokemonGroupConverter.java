package com.pogowiki.utils.converter;

import com.pogowiki.constant.PokemonGroup;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PokemonGroupConverter implements AttributeConverter<PokemonGroup, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PokemonGroup group) {
        if (group == null) {
            return null;
        }
        return group.getValue();
    }

    @Override
    public PokemonGroup convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return PokemonGroup.of(value);
    }
}
