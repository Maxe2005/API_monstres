package com.imt.Api_monstres.service.dto;

import com.imt.Api_monstres.Ratio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SkillServiceDto {
    private final String monsterId;
    private final Integer num;
    private final Double dmg;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Double lvlMax;
}
