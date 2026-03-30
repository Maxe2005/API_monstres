package com.imt.api_monstres.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.imt.api_monstres.Repository.dao.MonsterMongoDao;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MonsterRepository {
    private final MonsterMongoDao monsterMongoDao;

    public String save(MonsterMongoDto monsterMongoDto) {
        MonsterMongoDto savedMonsterDto = monsterMongoDao.save(monsterMongoDto);
        return savedMonsterDto.getMonsterId();
    }

    public void delete(String id) {
        monsterMongoDao.deleteById(id);
    }

    public Optional<MonsterMongoDto> findMonsterById(String monsterId) {
        return monsterMongoDao.findById(monsterId);
    }

    public List<MonsterMongoDto> findAllByPlayerId(String playerId) {
        return monsterMongoDao.findAllByPlayerId(playerId);
    }

    public List<MonsterMongoDto> findAllById(List<String> ids) {
        return monsterMongoDao.findAllById(ids);
    }

    public void update(MonsterMongoDto monsterMongoDto) {
        monsterMongoDao.save(monsterMongoDto);
    }

    public List<MonsterMongoDto> findAll() {
        return monsterMongoDao.findAll();
    }
}
