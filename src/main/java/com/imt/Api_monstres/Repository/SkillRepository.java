package com.imt.Api_monstres.Repository;

import com.imt.Api_monstres.Repository.dao.SkillMongoDao;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillRepository {
    private final SkillMongoDao skillMongoDao;

    public String save (SkillMongoDto skillMongoDto){
        SkillMongoDto savedSkillDto = skillMongoDao.save(skillMongoDto);
        return savedSkillDto.getSkillId();
    }

    public void delete (String id){
        skillMongoDao.deleteById(id);
    }

    public SkillMongoDto findSkillById (String skillId) {
        return skillMongoDao.findById(skillId).orElse(null);
    }

    public List<SkillMongoDto> findAllByMonsterId (String monsterId){
        return skillMongoDao.findAllByMonsterId(monsterId);
    }

    public void update(SkillMongoDto skillMongoDto) {
        skillMongoDao.save(skillMongoDto);
    }
}
