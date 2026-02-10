package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.enums.Stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RatioDto {
    private Stat stat;
    private double percent;
}
