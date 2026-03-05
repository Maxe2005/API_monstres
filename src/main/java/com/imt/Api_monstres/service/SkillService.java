package com.imt.api_monstres.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;


    public String createSkill (String monsterId, Integer number, Integer damage, Ratio ratio, Integer cooldown, Integer lvl, Integer lvlMax, Rank rank){
        String id = UUID.randomUUID().toString();
        SkillMongoDto skillToSave = new SkillMongoDto(
                id,
                monsterId,
                number,
                damage,
                ratio,
                cooldown,
                lvl,
                lvlMax,
                rank);
        return skillRepository.save(skillToSave);
    }


    public void deleteSkill(String skillId){
        if (skillRepository.findSkillById(skillId).isPresent()) {
            skillRepository.delete(skillId);
        } else {
            throw new RuntimeException("Skill introuvable :" + skillId);
        }
    }

    public SkillMongoDto getSkillById (String skillId) {
        return skillRepository.findSkillById(skillId).orElseThrow(() -> new RuntimeException("Skill introuvable :" + skillId));
    }

    public List<SkillMongoDto> getAllSkillsByMonsterId (String monsterId){
        return skillRepository.findAllByMonsterId(monsterId);
    }


    public void updateSkill(String skillId, Integer num, Integer dmg, Ratio ratio, Integer cooldown, Integer lvl){
        SkillMongoDto existingSkill = this.getSkillById(skillId);
        SkillMongoDto newSkillToSave = new SkillMongoDto(
                skillId,
                existingSkill.getMonsterId(),
                num != null ? num : existingSkill.getNumber(),
                dmg != null ? dmg : existingSkill.getDamage(),
                ratio != null ? ratio : existingSkill.getRatio(),
                cooldown != null ? cooldown : existingSkill.getCooldown(),
                lvl != null ? lvl : existingSkill.getLvl(),
                existingSkill.getLvlMax(),
                existingSkill.getRank());
        skillRepository.update(newSkillToSave);
    }
}
