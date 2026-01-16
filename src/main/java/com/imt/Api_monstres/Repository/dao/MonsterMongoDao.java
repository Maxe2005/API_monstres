package com.imt.Api_monstres.Repository.dao;

import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MonsterMongoDao extends MongoRepository <MonsterMongoDto,String> {
    public List<MonsterMongoDto> findAllByPlayerId(String id);
}
