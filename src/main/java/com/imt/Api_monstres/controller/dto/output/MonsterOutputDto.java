package com.imt.Api_monstres.controller.dto.output;

import java.util.List;

import com.imt.Api_monstres.utils.Elementary;
import com.imt.Api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final List<String> skills;
    private final Rank rank;
}
