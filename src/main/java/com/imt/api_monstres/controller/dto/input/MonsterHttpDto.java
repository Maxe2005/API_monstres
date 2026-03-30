package com.imt.api_monstres.controller.dto.input;

import java.util.List;

import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonsterHttpDto {
    private final String name;
    private final Elementary element;
    private final StatsDto stats;
    private final Rank rank;
    private final String cardDescription;
    private final String imageUrl;
    private final List<SkillHttpDto> skills;

    @Getter
    @RequiredArgsConstructor
    public static class StatsDto {
        private final Integer hp;
        private final Integer atk;
        private final Integer def;
        private final Integer vit;
    }
}
