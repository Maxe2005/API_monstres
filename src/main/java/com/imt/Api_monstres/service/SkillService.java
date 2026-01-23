package com.imt.Api_monstres.service;

import com.imt.Api_monstres.Repository.SkillRepository;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.utils.Ratio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public String createSkill (String monsterId, Integer num, Double dmg, Ratio ratio, Integer cooldown, Integer lvlMax){
        String id = UUID.randomUUID().toString();
        SkillMongoDto skillToSave = new SkillMongoDto(
                id,
                monsterId,
                num,
                dmg,
                ratio,
                cooldown,
                lvlMax);
        return skillRepository.save(skillToSave);
    }

    public void deleteSkill(String skillId){
        skillRepository.delete(skillId);
    }

    public SkillMongoDto getSkillById (String skillId) {
        return skillRepository.findSkillById(skillId);
    }

    public List<SkillMongoDto> getAllSkillsByMonsterId (String monsterId){
        return skillRepository.findAllByMonsterId(monsterId);
    }

    public void update(String id, String monsterId, Integer num, Double dmg, Ratio ratio, Integer cooldown, Integer lvlMax){
        SkillMongoDto newSkillToSave = new SkillMongoDto(
                id,
                monsterId,
                num,
                dmg,
                ratio,
                cooldown,
                lvlMax);
        skillRepository.update(newSkillToSave);
    }
}
