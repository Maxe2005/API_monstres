package com.imt.Api_monstres.controller.dto.output;

import com.imt.Api_monstres.utils.Ratio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SkillOutputDto {
    private final String skillId;
    private final String monsterId;
    private final Integer number;
    private final Double damage;
    private final Ratio ratio;
    private final Double cooldown;
    private final Double lvl;
    private final Double lvlMax;
}
