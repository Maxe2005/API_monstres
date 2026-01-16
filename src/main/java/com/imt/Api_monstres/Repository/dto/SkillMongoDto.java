package com.imt.Api_monstres.Repository.dto;

import com.imt.Api_monstres.Ratio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@RequiredArgsConstructor
@Document(collection = "skills")
public class SkillMongoDto {

    @MongoId
    private final String skillId;
    private final String monsterId;
    private final Integer num;
    private final Double dmg;
    private final Ratio ratio;
    private final Integer cooldown;
    private final Double lvlMax;
}


