package com.imt.Api_monstres.Repository;

import com.imt.Api_monstres.Repository.dao.SkillMongoDao;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SkillRepository {
    private final SkillMongoDao skillMongoDao;

    public SkillMongoDto save (SkillMongoDto skillMongoDto){
        return skillMongoDao.save(skillMongoDto);
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
