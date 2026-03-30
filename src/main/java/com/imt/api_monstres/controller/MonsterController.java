package com.imt.api_monstres.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imt.api_monstres.controller.dto.input.MonsterHttpDto;
import com.imt.api_monstres.controller.dto.output.CreateMonsterOutputDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.service.MonsterService;

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
    public ResponseEntity<MonsterOutputDto> getMonsterById(@Valid @PathVariable String monsterId,
            @RequestParam(required = false) boolean withSkills) {
        MonsterOutputDto monster = monsterService.getMonsterById(monsterId, withSkills);
        if (monster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monster);
    }

    @GetMapping("/getByPlayerId/{playerId}")
    public ResponseEntity<List<MonsterOutputDto>> getMonsterByPlayerId(@Valid @PathVariable String playerId,
            @RequestParam(required = false) boolean withSkills) {
        List<MonsterOutputDto> monsters = monsterService.getAllMonstersByPlayerId(playerId, withSkills);
        if (monsters.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monsters);

    }

    @GetMapping("/getByIds")
    public ResponseEntity<List<MonsterOutputDto>> getMonstersByIds(@RequestParam List<String> ids) {
        List<MonsterOutputDto> monsters = monsterService.getMonstersByIds(ids);
        if (monsters.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monsters);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MonsterOutputDto>> getAllMonsters() {
        List<MonsterOutputDto> monsters = monsterService.getAllMonsters();
        if (monsters.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monsters);
    }

    @DeleteMapping("/delete/{monsterId}")
    public ResponseEntity<String> deleteMonster(@Valid @PathVariable String monsterId) {
        monsterService.deleteMonster((monsterId));
        return ResponseEntity.ok("Monster deleted");
    }

    // @PostMapping("/updateMonster/{monsterId}")
    // public ResponseEntity<MonsterOutputDto> updateMonster (@Valid @PathVariable
    // String monsterId, @Valid @RequestBody MonsterHttpDto monsterHttpDto) {
    // monsterService.updateMonster(
    // monsterId,
    // monsterHttpDto.getPlayerId(),
    // monsterHttpDto.getElement(),
    // monsterHttpDto.getHp(),
    // monsterHttpDto.getAtk(),
    // monsterHttpDto.getDef(),
    // monsterHttpDto.getVit(),
    // monsterHttpDto.getSkills(),
    // monsterHttpDto.getRank());
    // MonsterMongoDto monsterMongoDto = monsterService.getMonsterById(monsterId);
    // MonsterOutputDto monsterToReturn = getMonsterOutputDto(monsterMongoDto);
    // return ResponseEntity.ok(monsterToReturn);
    // }
}
