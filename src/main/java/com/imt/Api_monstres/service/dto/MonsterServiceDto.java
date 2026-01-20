package com.imt.Api_monstres.service.dto;

import com.imt.Api_monstres.Elementary;
import com.imt.Api_monstres.Repository.dto.SkillMongoDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.List;
@Getter
@RequiredArgsConstructor
public class MonsterServiceDto {
    private final Elementary element;
    private final Double hp;
    private final Double atk;
    private final Double vit;
    private final List<SkillMongoDto> skillsList;
}
