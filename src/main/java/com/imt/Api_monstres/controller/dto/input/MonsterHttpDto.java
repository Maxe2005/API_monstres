package com.imt.Api_monstres.controller.dto.input;

import java.util.List;

import com.imt.Api_monstres.utils.Elementary;
import com.imt.Api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonsterHttpDto {
    private final Elementary element; 
    private final Double hp;
    private final Double atk;
    private final Double def;
    private final Double vit;
    private final List<SkillHttpDto> skills;
    private final Rank rank;

}
