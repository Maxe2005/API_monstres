package com.imt.api_monstres.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.imt.api_monstres.enums.Rank;

@Getter
@AllArgsConstructor
public class SkillForMonsterDto {

    private final int number;
    private final double damage;
    private final RatioDto ratio;
    private final double cooldown;
    private final double lvlMax;
    private final Rank rank;
}
