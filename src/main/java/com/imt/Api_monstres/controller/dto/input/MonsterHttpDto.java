package com.imt.Api_monstres.controller.dto.input;

import com.imt.Api_monstres.utils.Elementary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MonsterHttpDto {
    private final Elementary element;
    private final double hp;
    private final double atk;
    private final double def;
    private final double vit;
    private final List<SkillHttpDto> skillsList;

}
