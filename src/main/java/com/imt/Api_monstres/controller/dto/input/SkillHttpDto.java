package com.imt.Api_monstres.controller.dto.input;

import com.imt.Api_monstres.utils.Rank;
import com.imt.Api_monstres.utils.Ratio;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillHttpDto {
    private final Integer number;
    private final Double damage;
    private final Ratio ratio;
    private final Double cooldown;
    private final Double lvl;
    private final Double lvlMax;
    private final Rank rank;
}

