package com.imt.Api_monstres.Repository.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.imt.Api_monstres.utils.Elementary;

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
    private final Double hp;
    private final Double atk;
    private final Double def;
    private final Double vit;
    private final List<String> skills;
}
