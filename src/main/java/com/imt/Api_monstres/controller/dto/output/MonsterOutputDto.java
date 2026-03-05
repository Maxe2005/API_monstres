package com.imt.api_monstres.controller.dto.output;

import java.util.List;

import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonsterOutputDto {
    private final String monsterId;
    private final String playerId;
    private final Elementary element;
    private final Integer hp;
    private final Integer atk;
    private final Integer def;
    private final Integer vit;
    private final List<String> skills;
    private final Rank rank;
}
