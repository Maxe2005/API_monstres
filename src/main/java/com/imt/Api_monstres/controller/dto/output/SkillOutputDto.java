package com.imt.Api_monstres.controller.dto.output;

import com.imt.Api_monstres.utils.Ratio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SkillOutputDto {
    private final String skillId;
    private final String monsterId;
    private final Integer num;
    private final Double dmg;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Integer lvl;
    private final Integer lvlMax;
}
