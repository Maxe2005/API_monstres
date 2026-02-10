package com.imt.api_monstres.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatsDto {
    private long hp;
    private long atk;
    private long def;
    private long vit;
}