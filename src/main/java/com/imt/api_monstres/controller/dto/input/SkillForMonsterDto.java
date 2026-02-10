package com.imt.api_monstres.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.imt.api_monstres.enums.Rank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillForMonsterDto {

    private String name;
    private String description;
    private long damage;
    private RatioDto ratio;
    private long cooldown;
    private long lvlMax;
    private Rank rank;
}
