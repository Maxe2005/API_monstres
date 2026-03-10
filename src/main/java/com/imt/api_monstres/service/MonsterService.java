package com.imt.api_monstres.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private final SkillService skillService;

    public String createMonster (String playerId,Elementary element, Integer hp, Integer atk, Integer def, Integer vit, List<SkillHttpDto> skillsList, Rank rank){
        String monsterId = UUID.randomUUID().toString();
        List<String> skillIds = new ArrayList<>();
        for (SkillHttpDto skill: skillsList) {
            String skillCreated = skillService.createSkill(
                    monsterId,
                    skill.getNumber(),
                    skill.getDamage(),
                    skill.getRatio(),
                    skill.getCooldown(),
                    skill.getLvlMax(),
                    skill.getLvlMax(),
                    skill.getRank());
            skillIds.add(skillCreated);
        }
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                monsterId,
                playerId,
                element,
                hp,
                atk,
                def,
                vit,
                skillIds,
                rank
            );
        return monsterRepository.save(monsterToSave);
    }

    public void deleteMonster (String id) {
        if (monsterRepository.findMonsterById(id).isPresent()) {
            List<SkillMongoDto> listSkills = skillService.getAllSkillsByMonsterId(id);
            for (SkillMongoDto skill: listSkills) {
                skillService.deleteSkill(skill.getSkillId());
            }
            monsterRepository.delete(id);
        } else {
            throw new RuntimeException("Monstre introuvable :" + id);
        }
    }

    public MonsterMongoDto getMonsterById (String monsterId) {
        return monsterRepository.findMonsterById(monsterId).orElseThrow(() -> new RuntimeException("Monstre introuvable : " + monsterId));
    }

    public List<MonsterMongoDto> getAllMonstersByPlayerId(String playerId){
        return monsterRepository.findAllByPlayerId(playerId);
    }

    public void updateMonster(String monsterId, String playerId, Elementary element, Integer hp, Integer atk, Integer def, Integer vit, List<SkillHttpDto> skillHttpList, Rank rank){
        MonsterMongoDto existingMonster = this.getMonsterById(monsterId);
        List<String> finalSkillIds = new ArrayList<>();

        if (skillHttpList != null && !skillHttpList.isEmpty()) {
            List<SkillMongoDto> existingSkills = skillService.getAllSkillsByMonsterId(monsterId);
            for (SkillMongoDto s : existingSkills) {
                skillService.deleteSkill(s.getSkillId());
            }
            for (SkillHttpDto sk : skillHttpList) {
                String created = skillService.createSkill(
                        monsterId,
                        sk.getNumber(),
                        sk.getDamage(),
                        sk.getRatio(),
                        sk.getCooldown(),
                        sk.getLvl(),
                        sk.getLvlMax(),
                        sk.getRank());
                finalSkillIds.add(created);
            }
        } else {
            // garde les skills existants si aucune nouvelle liste n'est fournie
            if (existingMonster.getSkills() != null) {
                finalSkillIds.addAll(existingMonster.getSkills());
            }
        }

        MonsterMongoDto newMonsterToSave = new MonsterMongoDto(
                monsterId,
                playerId != null ? playerId : existingMonster.getPlayerId(),
                element != null ? element : existingMonster.getElement(),
                hp != null ? hp : existingMonster.getHp(),
                atk != null ? atk : existingMonster.getAtk(),
                def != null ? def : existingMonster.getDef(),
                vit != null ? vit : existingMonster.getVit(),
                finalSkillIds,
                rank != null ? rank : existingMonster.getRank());
        monsterRepository.update(newMonsterToSave);
    }
}
