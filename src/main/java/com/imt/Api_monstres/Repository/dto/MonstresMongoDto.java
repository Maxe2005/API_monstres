package com.imt.Api_monstres.Repository.dto;

import com.imt.Api_monstres.Elementary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@RequiredArgsConstructor
@Document(collection = "monstres") //Nom de la table de MongoDB
public class MonstresMongoDto {
    @MongoId
    private final String id;
    private final Elementary element;
    private final double hp;
    private final double atk;
    private final double vit;
}
