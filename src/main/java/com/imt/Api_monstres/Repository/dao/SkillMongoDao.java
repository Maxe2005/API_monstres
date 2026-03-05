package com.imt.api_monstres.Repository.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.imt.api_monstres.Repository.dto.SkillMongoDto;

import java.util.List;

public interface SkillMongoDao extends MongoRepository <SkillMongoDto, String>{
    public List<SkillMongoDto> findAllByMonsterId (String id);
}
