package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SkillHttpDto {
    private final Integer number;
    private final Integer damage;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Integer lvl;
    private final Integer lvlMax;
    private final Rank rank;
}

