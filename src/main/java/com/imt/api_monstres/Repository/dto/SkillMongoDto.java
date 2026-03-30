package com.imt.api_monstres.Repository.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Document(collection = "skills")
public class SkillMongoDto {

    @MongoId
    private final String skillId;
    private final String monsterId;
    private final String name;
    private final String description;
    private final Integer number;
    private final Integer damage;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Integer lvl;
    private final Integer lvlMax;
    private final Rank rank;
}
