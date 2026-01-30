package com.imt.Api_monstres.controller.dto;

import com.imt.Api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.controller.dto.input.MonsterHttpDto;
import com.imt.Api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.Api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.Api_monstres.service.MonsterService;
import com.imt.Api_monstres.service.SkillService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private final MonsterService monsterService;
    private final SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<String> createMonster(@Valid @RequestBody MonsterHttpDto monsterHttpDto) {
        String monsterId = monsterService.createMonster(
                monsterHttpDto.getElement(),
                monsterHttpDto.getHp(),
                monsterHttpDto.getAtk(),
                monsterHttpDto.getDef(),
                monsterHttpDto.getVit(),
                monsterHttpDto.getSkillsList());
        return ResponseEntity.status(HttpStatus.CREATED).body(monsterId);
    }

    @GetMapping("/{monsterId}")
    public ResponseEntity<MonsterOutputDto> getMonsterById(@Valid @PathVariable String monsterId) {
        MonsterMongoDto monsterMongoDto = monsterService.getMonsterById(monsterId);
        if (monsterMongoDto == null) {
            return ResponseEntity.notFound().build();
        }
        MonsterOutputDto monsterToReturn = getMonsterOutputDto(monsterMongoDto);
        return ResponseEntity.ok(monsterToReturn);
    }

    @GetMapping("/byPlayerId/{playerId}")
    public ResponseEntity<List<MonsterOutputDto>> getMonsterByPlayerId(@Valid @PathVariable String playerId) {
        List<MonsterMongoDto> ListMonsters = monsterService.getAllMonstersByPlayerId(playerId);
        if (ListMonsters.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<MonsterOutputDto> ListToReturn = new ArrayList<>();
        for (MonsterMongoDto monster: ListMonsters){
            ListToReturn.add(getMonsterOutputDto(monster));
        }
        return ResponseEntity.ok(ListToReturn);
    }

    @DeleteMapping("/{monsterId}")
    public ResponseEntity<String> deleteMonster (@Valid @PathVariable String monsterId){
        monsterService.deleteMonster((monsterId));
        return ResponseEntity.ok("Monster deleted");
    }

    @PostMapping("/updateMonster/{monsterId}")
    public ResponseEntity<MonsterOutputDto> updateMonster (@Valid String monsterId, @Valid String playerId,  @Valid @RequestBody MonsterHttpDto monsterHttpDto) {
        monsterService.updateMonster(
                monsterId,
                playerId,
                monsterHttpDto.getElement(),
                monsterHttpDto.getHp(),
                monsterHttpDto.getAtk(),
                monsterHttpDto.getDef(),
                monsterHttpDto.getVit(),
                skillService.getAllSkillsByMonsterId(monsterId));
        MonsterMongoDto monsterMongoDto = monsterService.getMonsterById(monsterId);
        MonsterOutputDto monsterToReturn = getMonsterOutputDto(monsterMongoDto);
        return ResponseEntity.ok(monsterToReturn);
    }

    @Nonnull
    private static MonsterOutputDto getMonsterOutputDto (MonsterMongoDto monsterMongoDto) {
        List<SkillOutputDto> skillsList = new ArrayList<>();
        for (SkillMongoDto skill : monsterMongoDto.getSkillsList()) {
            SkillOutputDto skillToReturn = new SkillOutputDto(
                    skill.getSkillId(),
                    skill.getMonsterId(),
                    skill.getNum(),
                    skill.getDmg(),
                    skill.getRatio(),
                    skill.getCooldown(),
                    skill.getLvl(),
                    skill.getLvlMax());
            skillsList.add(skillToReturn);
        }
        return new MonsterOutputDto(
                monsterMongoDto.getMonsterId(),
                monsterMongoDto.getPlayerId(),
                monsterMongoDto.getElement(),
                monsterMongoDto.getHp(),
                monsterMongoDto.getAtk(),
                monsterMongoDto.getDef(),
                monsterMongoDto.getVit(),
                skillsList);
    }
}
