package com.imt.Api_monstres.Repository;

import com.imt.Api_monstres.Repository.dao.MonsterMongoDao;
import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
@RequiredArgsConstructor
public class MonsterRepository {
    private final MonsterMongoDao monsterMongoDao;

    public String save (MonsterMongoDto monsterMongoDto){
        MonsterMongoDto savedMonsterDto = monsterMongoDao.save(monsterMongoDto);
        return savedMonsterDto.getMonsterId();
    }

    public void delete (String id){
        monsterMongoDao.deleteById(id);
    }

    public MonsterMongoDto findMonsterById (String monsterId) {
        return monsterMongoDao.findById(monsterId).orElse(null);
    }

    public List<MonsterMongoDto> findAllByPlayerId (String playerId){
        return monsterMongoDao.findAllByPlayerId(playerId);
    }

    public void update(MonsterMongoDto monsterMongoDto) {
        monsterMongoDao.save(monsterMongoDto);
    }
}
