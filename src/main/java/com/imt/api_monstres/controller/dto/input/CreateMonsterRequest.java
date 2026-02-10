package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.enums.Elementary;
import com.imt.api_monstres.enums.Rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMonsterRequest {
    private String name;
    private Elementary element;
    private StatsDto stats;
    private Rank rank;
    private String cardDescription;
    private String imageUrl;
    private List<SkillForMonsterDto> skills;
}
