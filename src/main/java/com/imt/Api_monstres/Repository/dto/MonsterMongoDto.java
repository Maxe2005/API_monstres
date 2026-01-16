package com.imt.Api_monstres.Repository.dto;

import com.imt.Api_monstres.Elementary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Document(collection = "monsters") //Nom de la table de MongoDB
public class MonsterMongoDto {

    @MongoId
    private final String monsterId;
    private final String playerId;
    private final Elementary element;
    private final Double hp;
    private final Double atk;
    private final Double vit;
    private final List<SkillMongoDto> skillsList;
}
