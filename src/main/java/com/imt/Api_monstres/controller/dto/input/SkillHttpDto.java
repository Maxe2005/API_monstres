package com.imt.Api_monstres.controller.dto.input;

import com.imt.Api_monstres.utils.Ratio;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillHttpDto {
    private final Integer num;
    private final Double dmg;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Integer lvlMax;
}

