package com.imt.api_monstres.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;

import com.imt.api_monstres.mapper.SkillMapper;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public String createSkill(String monsterId, SkillHttpDto dto) {
        String id = UUID.randomUUID().toString();
        SkillMongoDto skillToSave = SkillMapper.toMongoEntity(id, dto, monsterId);
        return skillRepository.save(skillToSave);
    }

    public void deleteSkill(String skillId) {
        if (skillRepository.findSkillById(skillId).isPresent()) {
            skillRepository.delete(skillId);
        } else {
            throw new RuntimeException("Skill introuvable :" + skillId);
        }
    }

    public SkillOutputDto getSkillById(String skillId) {
        SkillMongoDto skillMongo = skillRepository.findSkillById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill introuvable :" + skillId));
        return SkillMapper.toOutputDto(skillMongo);
    }

    public List<SkillOutputDto> getAllSkillsByMonsterId(String monsterId) {
        return skillRepository.findAllByMonsterId(monsterId).stream()
                .map(SkillMapper::toOutputDto)
                .toList();
    }

    public void updateSkill(String skillId, SkillHttpDto dto) {
        SkillMongoDto existing = skillRepository.findSkillById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill introuvable :" + skillId));
        SkillMongoDto newSkillToSave = new SkillMongoDto(
                skillId,
                existing.getMonsterId(),
                dto.getName() != null ? dto.getName() : existing.getName(),
                dto.getDescription() != null ? dto.getDescription() : existing.getDescription(),
                dto.getNumber() != null ? dto.getNumber() : existing.getNumber(),
                dto.getDamage() != null ? dto.getDamage() : existing.getDamage(),
                dto.getRatio() != null ? dto.getRatio() : existing.getRatio(),
                dto.getCooldown() != null ? dto.getCooldown() : existing.getCooldown(),
                dto.getLvl() != null ? dto.getLvl() : existing.getLvl(),
                dto.getLvlMax() != null ? dto.getLvlMax() : existing.getLvlMax(),
                dto.getRank() != null ? dto.getRank() : existing.getRank());
        skillRepository.update(newSkillToSave);
    }
}
