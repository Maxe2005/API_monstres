package com.imt.Api_monstres.service;

import com.imt.Api_monstres.Repository.SkillRepository;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import com.imt.Api_monstres.service.dto.SkillServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public void createSkill (SkillServiceDto skillServiceDto){
        String id = UUID.randomUUID().toString();
        SkillMongoDto skillToSave = new SkillMongoDto(
                id,
                skillServiceDto.getMonsterId(),
                skillServiceDto.getNum(),
                skillServiceDto.getDmg(),
                skillServiceDto.getRatio(),
                skillServiceDto.getCooldown(),
                skillServiceDto.getLvlMax());
        skillRepository.save(skillToSave);
    }

    public void deleteSkill(String skillId){
        skillRepository.delete(skillId);
    }

    public SkillMongoDto getSkillById (String skillId) {
        return skillRepository.findSkillById(skillId);
    }

    public List<SkillMongoDto> getAllSkillsByMonsterId (String monsterId){
        return skillRepository.findAllByMonsterId(monsterId);
    }

    public void update(String id, SkillMongoDto skillMongoDto){
        SkillMongoDto newSkillToSave = new SkillMongoDto(
                id,
                skillMongoDto.getMonsterId(),
                skillMongoDto.getNum(),
                skillMongoDto.getDmg(),
                skillMongoDto.getRatio(),
                skillMongoDto.getCooldown(),
                skillMongoDto.getLvlMax());
        skillRepository.update(newSkillToSave);
    }
}
