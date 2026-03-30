package com.imt.api_monstres.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private final SkillRepository skillRepository;
    private final SkillService skillService;

    public String createMonster(String playerId, Elementary element, Integer hp, Integer atk, Integer def, Integer vit,
            List<SkillHttpDto> skillsList, Rank rank) {
        String monsterId = UUID.randomUUID().toString();
        List<String> skillIds = new ArrayList<>();
        for (SkillHttpDto skill : skillsList) {
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
                rank);
        return monsterRepository.save(monsterToSave);
    }

    public void deleteMonster(String id) {
        if (monsterRepository.findMonsterById(id).isPresent()) {
            List<SkillOutputDto> listSkills = skillService.getAllSkillsByMonsterId(id);
            for (SkillOutputDto skill : listSkills) {
                if (skillRepository.findSkillById(skill.getSkillId()).isPresent()) {
                    skillService.deleteSkill(skill.getSkillId());
                }
            }
            monsterRepository.delete(id);
        } else {
            throw new RuntimeException("Monstre introuvable :" + id);
        }
    }

    public MonsterOutputDto getMonsterById(String monsterId, boolean withSkills) {
        MonsterMongoDto monster = monsterRepository.findMonsterById(monsterId)
                .orElseThrow(() -> new RuntimeException("Monstre introuvable : " + monsterId));
        if (withSkills) {
            List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monsterId);
            return new MonsterOutputDto(
                    monster.getMonsterId(),
                    monster.getPlayerId(),
                    monster.getElement(),
                    monster.getHp(),
                    monster.getAtk(),
                    monster.getDef(),
                    monster.getVit(),
                    skills,
                    monster.getRank());
        } else {
            return new MonsterOutputDto(
                    monster.getMonsterId(),
                    monster.getPlayerId(),
                    monster.getElement(),
                    monster.getHp(),
                    monster.getAtk(),
                    monster.getDef(),
                    monster.getVit(),
                    null,
                    monster.getRank());
        }
    }

    public List<MonsterOutputDto> getAllMonstersByPlayerId(String playerId, boolean withSkills) {
        List<MonsterMongoDto> monsters = monsterRepository.findAllByPlayerId(playerId);
        List<MonsterOutputDto> outputList = new ArrayList<>();
        for (MonsterMongoDto monster : monsters) {
            if (withSkills) {
                List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monster.getMonsterId());
                outputList.add(new MonsterOutputDto(
                        monster.getMonsterId(),
                        monster.getPlayerId(),
                        monster.getElement(),
                        monster.getHp(),
                        monster.getAtk(),
                        monster.getDef(),
                        monster.getVit(),
                        skills,
                        monster.getRank()));
            } else {
                outputList.add(new MonsterOutputDto(
                        monster.getMonsterId(),
                        monster.getPlayerId(),
                        monster.getElement(),
                        monster.getHp(),
                        monster.getAtk(),
                        monster.getDef(),
                        monster.getVit(),
                        null,
                        monster.getRank()));
            }
        }
        return outputList;
    }

    public List<MonsterOutputDto> getMonstersByIds(List<String> ids) {
        List<MonsterMongoDto> monsters = monsterRepository.findAllById(ids);
        return monsters.stream().map(monster -> {
            List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monster.getMonsterId());
            return new MonsterOutputDto(
                    monster.getMonsterId(),
                    monster.getPlayerId(),
                    monster.getElement(),
                    monster.getHp(),
                    monster.getAtk(),
                    monster.getDef(),
                    monster.getVit(),
                    skills,
                    monster.getRank());
        }).toList();
    }

    public List<MonsterOutputDto> getAllMonsters() {
        List<MonsterMongoDto> monsters = monsterRepository.findAll();
        return monsters.stream().map(monster -> {
            List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monster.getMonsterId());
            return new MonsterOutputDto(
                    monster.getMonsterId(),
                    monster.getPlayerId(),
                    monster.getElement(),
                    monster.getHp(),
                    monster.getAtk(),
                    monster.getDef(),
                    monster.getVit(),
                    skills,
                    monster.getRank());
        }).toList();
    }

    // public void updateMonster(String monsterId, String playerId, Elementary
    // element, Integer hp, Integer atk, Integer def, Integer vit,
    // List<SkillHttpDto> skillHttpList, Rank rank){
    // MonsterMongoDto existingMonster = this.getMonsterById(monsterId);
    // List<String> finalSkillIds = new ArrayList<>();
    //
    // if (skillHttpList != null && !skillHttpList.isEmpty()) {
    // List<SkillMongoDto> existingSkills =
    // skillService.getAllSkillsByMonsterId(monsterId);
    // for (SkillMongoDto s : existingSkills) {
    // skillService.deleteSkill(s.getSkillId());
    // }
    // for (SkillHttpDto sk : skillHttpList) {
    // String created = skillService.createSkill(
    // monsterId,
    // sk.getNumber(),
    // sk.getDamage(),
    // sk.getRatio(),
    // sk.getCooldown(),
    // sk.getLvl(),
    // sk.getLvlMax(),
    // sk.getRank());
    // finalSkillIds.add(created);
    // }
    // } else {
    // // garde les skills existants si aucune nouvelle liste n'est fournie
    // if (existingMonster.getSkills() != null) {
    // finalSkillIds.addAll(existingMonster.getSkills());
    // }
    // }
    //
    // MonsterMongoDto newMonsterToSave = new MonsterMongoDto(
    // monsterId,
    // playerId != null ? playerId : existingMonster.getPlayerId(),
    // element != null ? element : existingMonster.getElement(),
    // hp != null ? hp : existingMonster.getHp(),
    // atk != null ? atk : existingMonster.getAtk(),
    // def != null ? def : existingMonster.getDef(),
    // vit != null ? vit : existingMonster.getVit(),
    // finalSkillIds,
    // rank != null ? rank : existingMonster.getRank());
    // monsterRepository.update(newMonsterToSave);
    // }
}
