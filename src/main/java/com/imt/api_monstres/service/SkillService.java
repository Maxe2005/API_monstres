package com.imt.api_monstres.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.api_monstres.exception.SkillNotFoundException;

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
            throw new SkillNotFoundException(skillId);
        }
    }

    public SkillOutputDto getSkillById(String skillId) {
        SkillMongoDto skillMongo = skillRepository.findSkillById(skillId)
                .orElseThrow(() -> new SkillNotFoundException(skillId));
        return SkillMapper.toOutputDto(skillMongo);
    }

    public List<SkillOutputDto> getAllSkillsByMonsterId(String monsterId) {
        return skillRepository.findAllByMonsterId(monsterId).stream()
                .map(SkillMapper::toOutputDto)
                .toList();
    }
}
