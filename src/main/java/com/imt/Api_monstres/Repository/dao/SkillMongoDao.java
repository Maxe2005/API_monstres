package com.imt.Api_monstres.Repository.dao;

import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillMongoDao extends MongoRepository <SkillMongoDto, String>{
    public List<SkillMongoDto> findAllByMonsterId (String id);
}
