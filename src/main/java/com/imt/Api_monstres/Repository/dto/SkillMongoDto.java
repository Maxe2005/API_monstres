package com.imt.Api_monstres.Repository.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.Api_monstres.utils.Ratio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Document(collection = "skills")
public class SkillMongoDto {

    @MongoId
    private final String skillId;
    private final String monsterId;
    private final Integer number;
    private final Double damage;
    private final Ratio ratio;
    private final Double cooldown;
    private final Double lvl;
    private final Double lvlMax;
}


