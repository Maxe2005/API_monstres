package com.imt.Api_monstres.service;

import com.imt.Api_monstres.Repository.MonsterRepository;
import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.Api_monstres.service.dto.MonsterServiceDot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterService {
    private final MonsterRepository monsterRepository;
    //TODO : gérer playerid
    public String createMonster (MonsterServiceDot monsterServiceDto){
        String id = UUID.randomUUID().toString();
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                id,
                monsterServiceDto.getPlayerId(),
                monsterServiceDto.getElement(),
                monsterServiceDto.getHp(),
                monsterServiceDto.getAtk(),
                monsterServiceDto.getVit(),
                monsterServiceDto.getSkillsList());
        monsterRepository.save(monsterToSave);
        return id;
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
        //TODO : gérer playerid
        MonsterMongoDto monsterToSave = new MonsterMongoDto(
                id,
                monsterMongoDto.getPlayerId(),
                monsterMongoDto.getElement(),
                monsterMongoDto.getHp(),
                monsterMongoDto.getAtk(),
                monsterMongoDto.getVit(),
                monsterMongoDto.getSkillsList());
        monsterRepository.save(monsterToSave);
    }
}
