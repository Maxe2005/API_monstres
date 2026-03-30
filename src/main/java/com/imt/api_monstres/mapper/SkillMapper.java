package com.imt.api_monstres.mapper;

import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillMapper {

    public static SkillMongoDto toMongoEntity(String id, SkillHttpDto dto, String monsterId) {
        return new SkillMongoDto(
                id,
                monsterId,
                dto.getName(),
                dto.getDescription(),
                dto.getNumber(),
                dto.getDamage(),
                dto.getRatio(),
                dto.getCooldown(),
                dto.getLvl(),
                dto.getLvlMax(),
                dto.getRank());
    }

    public static SkillOutputDto toOutputDto(SkillMongoDto entity) {
        return new SkillOutputDto(
                entity.getSkillId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDamage(),
                new SkillOutputDto.RatioDto(entity.getRatio().getStat().name(), entity.getRatio().getPercent()),
                entity.getCooldown(),
                entity.getLvlMax(),
                entity.getRank());
    }
}