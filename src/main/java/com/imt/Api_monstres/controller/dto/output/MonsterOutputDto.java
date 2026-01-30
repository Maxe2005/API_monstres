package com.imt.Api_monstres.controller.dto.output;

import com.imt.Api_monstres.utils.Elementary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MonsterOutputDto {
    private final String monsterId;
    private final String playerId;
    private final Elementary element;
    private final Double hp;
    private final Double atk;
    private final Double def;
    private final Double vit;
    private final List<SkillOutputDto> skillsList;
}
