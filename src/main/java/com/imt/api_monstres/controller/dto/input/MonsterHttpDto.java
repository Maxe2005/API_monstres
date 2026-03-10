package com.imt.api_monstres.controller.dto.input;

import java.util.List;

import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonsterHttpDto {
    private final String playerId;
    private final Elementary element; 
    private final Integer hp;
    private final Integer atk;
    private final Integer def;
    private final Integer vit;
    private final List<SkillHttpDto> skills;
    private final Rank rank;

}
