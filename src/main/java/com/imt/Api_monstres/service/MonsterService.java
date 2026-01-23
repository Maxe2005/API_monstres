package com.imt.Api_monstres.service;

import com.imt.Api_monstres.Repository.MonsterRepository;
import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.utils.Elementary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private SkillService skillService;

    public String createMonster (Elementary element, Double hp, Double atk, Double def, Double vit, List<SkillMongoDto> skillsList){
        String id = UUID.randomUUID().toString();
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                id,
                null,
                element,
                hp,
                atk,
                def,
                vit,
                skillsList);
        return monsterRepository.save(monsterToSave);
    }

    public void deleteMonster (String id) {
        List<SkillMongoDto> listSkills = skillService.getAllSkillsByMonsterId(id);
        for (SkillMongoDto skill: listSkills) {
            skillService.deleteSkill(skill.getSkillId());
        }
        monsterRepository.delete(id);
    }

    public MonsterMongoDto getMonsterById (String monsterId) {
        return monsterRepository.findMonsterById(monsterId);
    }

    public List<MonsterMongoDto> getAllMonstersByPlayerId(String playerId){
        return monsterRepository.findAllByPlayerId(playerId);
    }

    public void updateMonster(String monsterId, String playerId, Elementary element, Double hp, Double atk, Double def, Double vit, List<SkillMongoDto> skillsList)  {
        MonsterMongoDto existingMonster = this.getMonsterById(monsterId);
        MonsterMongoDto newMonsterToSave = new MonsterMongoDto(
                monsterId,
                playerId != null ? playerId : existingMonster.getPlayerId(),
                element != null ? element : existingMonster.getElement(),
                hp != null ? hp : existingMonster.getHp(),
                atk != null ? atk : existingMonster.getAtk(),
                def != null ? def : existingMonster.getDef(),
                vit != null ? vit : existingMonster.getVit(),
                skillsList != null ? skillsList : existingMonster.getSkillsList());
        monsterRepository.update(newMonsterToSave);
    }
}
