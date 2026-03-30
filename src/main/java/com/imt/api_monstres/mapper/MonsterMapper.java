package com.imt.api_monstres.mapper;

import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.controller.dto.input.MonsterHttpDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonsterMapper {

    public static MonsterMongoDto toMongoEntity(String monsterId, MonsterHttpDto dto, List<String> skillIds) {
        return new MonsterMongoDto(
                monsterId,
                null,
                dto.getElement(),
                dto.getStats().getHp(),
                dto.getStats().getAtk(),
                dto.getStats().getDef(),
                dto.getStats().getVit(),
                skillIds,
                dto.getRank(),
                dto.getName(),
                dto.getCardDescription(),
                dto.getImageUrl());
    }

    public static MonsterOutputDto toOutputDto(MonsterMongoDto entity, List<SkillOutputDto> skills) {
        return new MonsterOutputDto(
                entity.getMonsterId(),
                entity.getName(),
                entity.getElement(),
                new MonsterOutputDto.StatsDto(
                        entity.getHp(),
                        entity.getAtk(),
                        entity.getDef(),
                        entity.getVit()),
                entity.getRank(),
                entity.getCardDescription(),
                entity.getImageUrl(),
                skills);
    }
}