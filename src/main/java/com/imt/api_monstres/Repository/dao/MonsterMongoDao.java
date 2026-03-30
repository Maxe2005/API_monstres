package com.imt.api_monstres.Repository.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.imt.api_monstres.Repository.dto.MonsterMongoDto;

import java.util.List;

public interface MonsterMongoDao extends MongoRepository<MonsterMongoDto, String> {
    public List<MonsterMongoDto> findAllByPlayerId(String id);

    public List<MonsterMongoDto> findAllById(Iterable<String> ids);
}
