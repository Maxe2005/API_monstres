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


    public SkillMongoDto createSkill (String monsterId, Integer num, Double dmg, Ratio ratio, Integer cooldown, Integer lvl, Integer lvlMax){
        String id = UUID.randomUUID().toString();
        SkillMongoDto skillToSave = new SkillMongoDto(
                id,
                monsterId,
                num,
                dmg,
                ratio,
                cooldown,
                lvl,
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


    public void updateSkill(String skillId, Integer num, Double dmg, Ratio ratio, Integer cooldown, Integer lvl){
        SkillMongoDto existingSkill = this.getSkillById(skillId);
        SkillMongoDto newSkillToSave = new SkillMongoDto(
                skillId,
                existingSkill.getMonsterId(),
                num != null ? num : existingSkill.getNum(),
                dmg != null ? dmg : existingSkill.getDmg(),
                ratio != null ? ratio : existingSkill.getRatio(),
                cooldown != null ? cooldown : existingSkill.getCooldown(),
                lvl != null ? lvl : existingSkill.getLvl(),
                existingSkill.getLvlMax());
        skillRepository.update(newSkillToSave);
    }
}
