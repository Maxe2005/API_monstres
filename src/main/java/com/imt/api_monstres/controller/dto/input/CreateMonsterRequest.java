package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.enums.Elementary;
import com.imt.api_monstres.enums.Rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateMonsterRequest {
    private String name;
    private Elementary element;
    private StatsDto stats;
    private Rank rank;
    private String cardDescription;
    private String imageUrl;
    private List<SkillForMonsterDto> skills;

    @Getter
    @AllArgsConstructor
    class StatsDto {
        private long hp;
        private long atk;
        private long def;
        private long vit;
    }
}
