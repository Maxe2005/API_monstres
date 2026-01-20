package com.imt.api_monstres.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Stat {
    HP("hp"),
    ATK("atk"),
    DEF("def"),
    VIT("vit");

    private final String type;
}
