package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.enums.Stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RatioDto {
    private final Stat stat;
    private final double percent;
}
