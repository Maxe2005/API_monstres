package com.imt.api_monstres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.controller.dto.input.MonsterHttpDto;
import com.imt.api_monstres.controller.dto.output.CreateMonsterOutputDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.service.MonsterService;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("api/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private final MonsterService monsterService;

    @PostMapping("/create")
    public ResponseEntity<CreateMonsterOutputDto> createMonster(@Valid @RequestBody MonsterHttpDto monsterHttpDto) {
        String monsterId = monsterService.createMonster(
                monsterHttpDto.getPlayerId(),
                monsterHttpDto.getElement(),
                monsterHttpDto.getHp(),
                monsterHttpDto.getAtk(),
                monsterHttpDto.getDef(),
                monsterHttpDto.getVit(),
                monsterHttpDto.getSkills(),
                monsterHttpDto.getRank());
        CreateMonsterOutputDto dtoToReturn = new CreateMonsterOutputDto(monsterId, "Monstre créé avec succès");
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoToReturn);
    }

    @GetMapping("/get/{monsterId}")
    public ResponseEntity<MonsterOutputDto> getMonsterById(@Valid @PathVariable String monsterId) {
        MonsterMongoDto monsterMongoDto = monsterService.getMonsterById(monsterId);
        if (monsterMongoDto == null) {
            return ResponseEntity.notFound().build();
        }
        MonsterOutputDto monsterToReturn = getMonsterOutputDto(monsterMongoDto);
        return ResponseEntity.ok(monsterToReturn);
    }

    @GetMapping("/getByPlayerId/{playerId}")
    public ResponseEntity<List<MonsterOutputDto>> getMonsterByPlayerId(@Valid @PathVariable String playerId) {
        List<MonsterMongoDto> listMonsters = monsterService.getAllMonstersByPlayerId(playerId);
        if (listMonsters.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<MonsterOutputDto> listToReturn = new ArrayList<>();
        for (MonsterMongoDto monster: listMonsters){
            listToReturn.add(getMonsterOutputDto(monster));
        }
        return ResponseEntity.ok(listToReturn);
    }

    @DeleteMapping("/delete/{monsterId}")
    public ResponseEntity<String> deleteMonster (@Valid @PathVariable String monsterId){
        monsterService.deleteMonster((monsterId));
        return ResponseEntity.ok("Monster deleted");
    }

//    @PostMapping("/updateMonster/{monsterId}")
//    public ResponseEntity<MonsterOutputDto> updateMonster (@Valid @PathVariable String monsterId, @Valid @RequestBody MonsterHttpDto monsterHttpDto) {
//    monsterService.updateMonster(
//        monsterId,
//        monsterHttpDto.getPlayerId(),
//        monsterHttpDto.getElement(),
//        monsterHttpDto.getHp(),
//        monsterHttpDto.getAtk(),
//        monsterHttpDto.getDef(),
//        monsterHttpDto.getVit(),
//        monsterHttpDto.getSkills(),
//        monsterHttpDto.getRank());
//    MonsterMongoDto monsterMongoDto = monsterService.getMonsterById(monsterId);
//    MonsterOutputDto monsterToReturn = getMonsterOutputDto(monsterMongoDto);
//    return ResponseEntity.ok(monsterToReturn);
//    }

    @Nonnull
    private static MonsterOutputDto getMonsterOutputDto (MonsterMongoDto monsterMongoDto) {
        return new MonsterOutputDto(
                monsterMongoDto.getMonsterId(),
                monsterMongoDto.getPlayerId(),
                monsterMongoDto.getElement(),
                monsterMongoDto.getHp(),
                monsterMongoDto.getAtk(),
                monsterMongoDto.getDef(),
                monsterMongoDto.getVit(),
                monsterMongoDto.getSkills(),
                monsterMongoDto.getRank());
    }
}
