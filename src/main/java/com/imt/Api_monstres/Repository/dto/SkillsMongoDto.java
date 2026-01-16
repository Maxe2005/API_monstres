package com.imt.Api_monstres.Repository.dto;

import com.imt.Api_monstres.Ratio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@RequiredArgsConstructor
@Document(collection = "skills")
public class SkillsMongoDto {
    private final String id;
    private final Integer num;
    private final double dmg;
    private final Ratio ratio;
    private final Integer cooldown;
    private final double lvlMax;
}


