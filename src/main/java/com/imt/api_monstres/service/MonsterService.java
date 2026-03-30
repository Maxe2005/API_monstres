package com.imt.api_monstres.service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.controller.dto.input.MonsterHttpDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.api_monstres.mapper.MonsterMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;
    private final SkillRepository skillRepository;
    private final SkillService skillService;

    public String createMonster(MonsterHttpDto dto) {
        String monsterId = UUID.randomUUID().toString();
        List<String> skillIds = dto.getSkills().stream()
                .map(skill -> skillService.createSkill(monsterId, skill))
                .toList();
        MonsterMongoDto monsterToSave = MonsterMapper.toMongoEntity(monsterId, dto, skillIds);
        return monsterRepository.save(monsterToSave);
    }

    public void deleteMonster(String id) {
        if (monsterRepository.findMonsterById(id).isPresent()) {
            List<SkillOutputDto> listSkills = skillService.getAllSkillsByMonsterId(id);
            for (SkillOutputDto skill : listSkills) {
                if (skillRepository.findSkillById(skill.getId()).isPresent()) {
                    skillService.deleteSkill(skill.getId());
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
        List<SkillOutputDto> skills = withSkills ? skillService.getAllSkillsByMonsterId(monsterId) : null;
        return MonsterMapper.toOutputDto(monster, skills);
    }

    public List<MonsterOutputDto> getAllMonstersByPlayerId(String playerId, boolean withSkills) {
        List<MonsterMongoDto> monsters = monsterRepository.findAllByPlayerId(playerId);
        List<MonsterOutputDto> outputList = new ArrayList<>();
        for (MonsterMongoDto monster : monsters) {
            List<SkillOutputDto> skills = withSkills ? skillService.getAllSkillsByMonsterId(monster.getMonsterId())
                    : null;
            outputList.add(this.mapToOutputDto(monster, skills));
        }
        return outputList;
    }

    public List<MonsterOutputDto> getMonstersByIds(List<String> ids) {
        List<MonsterMongoDto> monsters = monsterRepository.findAllById(ids);
        return monsters.stream().map(monster -> {
            List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monster.getMonsterId());
            return this.mapToOutputDto(monster, skills);
        }).toList();
    }

    public List<MonsterOutputDto> getAllMonsters() {
        List<MonsterMongoDto> monsters = monsterRepository.findAll();
        return monsters.stream().map(monster -> {
            List<SkillOutputDto> skills = skillService.getAllSkillsByMonsterId(monster.getMonsterId());
            return this.mapToOutputDto(monster, skills);
        }).toList();
    }

    public MonsterOutputDto mapToOutputDto(MonsterMongoDto monster, List<SkillOutputDto> skills) {
        return new MonsterOutputDto(
                monster.getMonsterId(),
                monster.getName(),
                monster.getElement(),
                new MonsterOutputDto.StatsDto(
                        monster.getHp(),
                        monster.getAtk(),
                        monster.getDef(),
                        monster.getVit()),
                monster.getRank(),
                monster.getCardDescription(),
                monster.getImageUrl(),
                skills);
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
