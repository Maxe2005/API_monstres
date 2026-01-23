package com.imt.Api_monstres.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Elementary {
    FIRE("fire"),
    WATER("water"),
    WIND("wind");
    private final String name;
}
