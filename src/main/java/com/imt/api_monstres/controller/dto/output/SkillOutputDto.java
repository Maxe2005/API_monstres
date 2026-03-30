package com.imt.api_monstres.controller.dto.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.imt.api_monstres.utils.Rank;

@Getter
@RequiredArgsConstructor
public class SkillOutputDto {
    private final String id;
    private final String name;
    private final String description;
    private final Integer damage;
    private final RatioDto ratio;
    private final Integer cooldown;
    private final Integer lvlMax;
    private final Rank rank;

    @Getter
    @RequiredArgsConstructor
    public static class RatioDto {
        private final String stat;
        private final Double percent;
    }
}
