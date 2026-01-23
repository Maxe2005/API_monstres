package com.imt.Api_monstres.controller.dto;

import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.Api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.Api_monstres.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/monsters/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<String> createSkill(@Valid String monsterId, @Valid @RequestBody SkillHttpDto skillHttpDto){
        String skillId = skillService.createSkill(
                monsterId,
                skillHttpDto.getNum(),
                skillHttpDto.getDmg(),
                skillHttpDto.getRatio(),
                skillHttpDto.getCooldown(),
                1,
                skillHttpDto.getLvlMax());
        return ResponseEntity.status(HttpStatus.CREATED).body(skillId);
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillOutputDto> getSkillById(@Valid @PathVariable String skillId){
        SkillMongoDto skillMongoDto = skillService.getSkillById(skillId);;
        if (skillMongoDto == null){
            return ResponseEntity.notFound().build();
        }
        SkillOutputDto skillToReturn = new SkillOutputDto(
                skillMongoDto.getSkillId(),
                skillMongoDto.getMonsterId(),
                skillMongoDto.getNum(),
                skillMongoDto.getDmg(),
                skillMongoDto.getRatio(),
                skillMongoDto.getCooldown(),
                skillMongoDto.getLvl(),
                skillMongoDto.getLvlMax());
        return ResponseEntity.ok(skillToReturn);
    }

    @GetMapping("/{monsterId}")
    public ResponseEntity<List<SkillOutputDto>> getAllSkillsByMonsterId(@Valid @PathVariable String monsterId){
        List<SkillMongoDto> listSkills = skillService.getAllSkillsByMonsterId(monsterId);;
        List<SkillOutputDto> listToReturn = new ArrayList<>();
        if (listSkills.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for (SkillMongoDto skill: listSkills) {
            SkillOutputDto skillToReturn = new SkillOutputDto(
                    skill.getSkillId(),
                    skill.getMonsterId(),
                    skill.getNum(),
                    skill.getDmg(),
                    skill.getRatio(),
                    skill.getCooldown(),
                    skill.getLvl(),
                    skill.getLvlMax());
            listToReturn.add(skillToReturn);
        }
        return ResponseEntity.ok(listToReturn);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteSkill (@Valid @PathVariable String skillId){
        skillService.deleteSkill((skillId));
        return ResponseEntity.ok("Skill deleted");
    }

//    @PostMapping("/updateSkill/{skillId}")
//    public ResponseEntity<String> updateSkill (@Valid String skillId, @Valid @RequestBody SkillHttpDto skillHttpDto) {
//        skillService.updateSkill();
//    }
}
