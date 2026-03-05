package com.imt.api_monstres.Repository.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Document(collection = "monsters") //Nom de la table de MongoDB
public class MonsterMongoDto {

    @MongoId
    private final String monsterId;
    private final String playerId;
    private final Elementary element;
    private final Integer hp;
    private final Integer atk;
    private final Integer def;
    private final Integer vit;
    private final List<String> skills;
    private final Rank rank;
}
