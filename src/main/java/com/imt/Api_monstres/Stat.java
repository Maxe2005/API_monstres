package com.imt.Api_monstres;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Stat {
    ATK("atk"),
    DEF("def"),
    VIT("vit");
    private final String name;
}
