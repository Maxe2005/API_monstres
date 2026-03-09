package com.imt.api_monstres.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.imt.api_monstres.Repository.dao.SkillMongoDao;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SkillRepository {
    private final SkillMongoDao skillMongoDao;

    public String save (SkillMongoDto skillMongoDto){
        SkillMongoDto skill = skillMongoDao.save(skillMongoDto);
        return skill.getSkillId();
    }

    public void delete (String id){
        skillMongoDao.deleteById(id);
    }

    public Optional<SkillMongoDto> findSkillById (String skillId) {
        return skillMongoDao.findById(skillId);
    }

    public List<SkillMongoDto> findAllByMonsterId (String monsterId){
        return skillMongoDao.findAllByMonsterId(monsterId);
    }

    public void update(SkillMongoDto skillMongoDto) {
        skillMongoDao.save(skillMongoDto);
    }
}
