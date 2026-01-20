package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.enums.Elementary;
import com.imt.api_monstres.enums.Rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateMonsterRequest {

    private Elementary element;
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer vit;
    private List<SkillForMonsterDto> skills;
    private Rank rank;
}
