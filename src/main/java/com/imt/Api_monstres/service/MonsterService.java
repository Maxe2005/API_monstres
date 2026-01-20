package com.imt.Api_monstres.service;

import com.imt.Api_monstres.Repository.MonsterRepository;
import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.Api_monstres.service.dto.MonsterServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterService {

    private final MonsterRepository monsterRepository;

    public String createMonster (MonsterServiceDto monsterServiceDto){
        String id = UUID.randomUUID().toString();
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                id,
                null,
                monsterServiceDto.getElement(),
                monsterServiceDto.getHp(),
                monsterServiceDto.getAtk(),
                monsterServiceDto.getVit(),
                monsterServiceDto.getSkillsList());
        return monsterRepository.save(monsterToSave);
    }

    public void deleteMonster(String id) {
        monsterRepository.delete(id);
    }

    public MonsterMongoDto getMonsterById(String monsterId) {
        return monsterRepository.findMonsterById(monsterId);
    }

    public List<MonsterMongoDto> getAllMonstersByPlayerId(String playerId){
        return monsterRepository.findAllByPlayerId(playerId);
    }

    public void updateMonsterById(String id, MonsterMongoDto monsterMongoDto) {
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                id,
                monsterMongoDto.getPlayerId(),
                monsterMongoDto.getElement(),
                monsterMongoDto.getHp(),
                monsterMongoDto.getAtk(),
                monsterMongoDto.getVit(),
                monsterMongoDto.getSkillsList());
        monsterRepository.update(monsterToSave);
    }
}
