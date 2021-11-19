package com.pogowiki.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum PokemonGroup {
    NORMAL(1, "Normal Pokemon"),
    BABY(2, "Baby Pokemon"),
    PSEUDO(3, "Pseudo Pokemon"),
    MYTHICAL(4, "Mythical Pokemon"),
    LEGENDARY(5, "Legendary Pokemon");

    private final int value;
    private final String name;

    PokemonGroup(int status, String name) {
        this.value = status;
        this.name = name;
    }

    public static PokemonGroup of(int value) {
        return Arrays.stream(values()).filter(e -> e.value == value).findFirst().orElse(null);
    }

    public static String getName(int value) {
        PokemonGroup group = Arrays.stream(values()).filter(e -> e.value == value).findFirst().orElse(null);
        return group == null ? null : group.name;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
