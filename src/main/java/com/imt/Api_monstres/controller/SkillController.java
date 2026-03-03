package com.imt.Api_monstres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.Api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.Api_monstres.service.SkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//TODO: Ajouter @Operation, @ApiResponse, @Parameter et @Tag pour la doc swagger
// Ajouter des Try catch pour les erreurs, et faire en sorte que les delete ne renvoient pas d'erreur si le skill n'existe pas
@RestController
@RequestMapping("api/monsters/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;
// On crée les skills en même temps que les monstres
//    @PostMapping("/create")
//    public ResponseEntity<String> createSkill(@Valid String monsterId, @Valid @RequestBody SkillHttpDto skillHttpDto){
//        SkillMongoDto skill = skillService.createSkill(
//                monsterId,
//                skillHttpDto.getNum(),
//                skillHttpDto.getDmg(),
//                skillHttpDto.getRatio(),
//                skillHttpDto.getCooldown(),
//                1,
//                skillHttpDto.getLvlMax());
//        return ResponseEntity.status(HttpStatus.CREATED).body(skill.getSkillId());
//    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillOutputDto> getSkillById(@Valid @PathVariable String skillId){
        SkillMongoDto skillMongoDto = skillService.getSkillById(skillId);
        if (skillMongoDto == null){
            return ResponseEntity.notFound().build();
        }
        SkillOutputDto skillToReturn = new SkillOutputDto(
                skillMongoDto.getSkillId(),
                skillMongoDto.getMonsterId(),
                skillMongoDto.getNumber(),
                skillMongoDto.getDamage(),
                skillMongoDto.getRatio(),
                skillMongoDto.getCooldown(),
                skillMongoDto.getLvl(),
                skillMongoDto.getLvlMax());
        return ResponseEntity.ok(skillToReturn);
    }

    @GetMapping("/byMonsterId/{monsterId}")
    public ResponseEntity<List<SkillOutputDto>> getAllSkillsByMonsterId(@Valid @PathVariable String monsterId){
        List<SkillMongoDto> listSkills = skillService.getAllSkillsByMonsterId(monsterId);
        List<SkillOutputDto> listToReturn = new ArrayList<>();
        if (listSkills.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for (SkillMongoDto skill: listSkills) {
            SkillOutputDto skillToReturn = new SkillOutputDto(
                    skill.getSkillId(),
                    skill.getMonsterId(),
                    skill.getNumber(),
                    skill.getDamage(),
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

    @PostMapping("/updateSkill/{skillId}")
    public ResponseEntity<SkillOutputDto> updateSkill (@Valid @PathVariable String skillId, @Valid @RequestBody SkillHttpDto skillHttpDto) {
        skillService.updateSkill(
                skillId,
                skillHttpDto.getNumber(),
                skillHttpDto.getDamage(),
                skillHttpDto.getRatio(),
                skillHttpDto.getCooldown(),
                skillHttpDto.getLvl());
        SkillMongoDto skillMongoDto = skillService.getSkillById(skillId);
        SkillOutputDto skillToReturn = new SkillOutputDto(
                skillMongoDto.getSkillId(),
                skillMongoDto.getMonsterId(),
                skillMongoDto.getNumber(),
                skillMongoDto.getDamage(),
                skillMongoDto.getRatio(),
                skillMongoDto.getCooldown(),
                skillMongoDto.getLvl(),
                skillMongoDto.getLvlMax());
        return ResponseEntity.ok(skillToReturn);
    }
}
