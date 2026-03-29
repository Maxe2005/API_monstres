package com.imt.api_monstres.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.api_monstres.service.SkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/monsters/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/get/{skillId}")
    public ResponseEntity<SkillOutputDto> getSkillById(@Valid @PathVariable String skillId){
        SkillOutputDto skill = skillService.getSkillById(skillId);
        if (skill == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skill);
    }

    @GetMapping("/getByMonsterId/{monsterId}")
    public ResponseEntity<List<SkillOutputDto>> getAllSkillsByMonsterId(@Valid @PathVariable String monsterId){
        List<SkillOutputDto> listSkills = skillService.getAllSkillsByMonsterId(monsterId);
        if (listSkills.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listSkills);
    }

    @DeleteMapping("/delete/{skillId}")
    public ResponseEntity<String> deleteSkill (@Valid @PathVariable String skillId){
        skillService.deleteSkill((skillId));
        return ResponseEntity.ok("Skill deleted");
    }

//    @PostMapping("/updateSkill/{skillId}")
//    public ResponseEntity<SkillOutputDto> updateSkill (@Valid @PathVariable String skillId, @Valid @RequestBody SkillHttpDto skillHttpDto) {
//        skillService.updateSkill(
//                skillId,
//                skillHttpDto.getNumber(),
//                skillHttpDto.getDamage(),
//                skillHttpDto.getRatio(),
//                skillHttpDto.getCooldown(),
//                skillHttpDto.getLvl(),
//                skillHttpDto.getLvlMax(),
//                skillHttpDto.getRank());
//        SkillMongoDto skillMongoDto = skillService.getSkillById(skillId);
//        SkillOutputDto skillToReturn = new SkillOutputDto(
//                skillMongoDto.getSkillId(),
//                skillMongoDto.getMonsterId(),
//                skillMongoDto.getNumber(),
//                skillMongoDto.getDamage(),
//                skillMongoDto.getRatio(),
//                skillMongoDto.getCooldown(),
//                skillMongoDto.getLvl(),
//                skillMongoDto.getLvlMax(),
//                skillMongoDto.getRank());
//        return ResponseEntity.ok(skillToReturn);
//    }
}
