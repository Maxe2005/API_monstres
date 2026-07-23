package com.imt.api_monstres.controller.dto.input;

import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SkillHttpDto {
    @NotBlank
    private final String name;
    private final String description;
    @NotNull
    @PositiveOrZero
    private final Integer number;
    @NotNull
    @PositiveOrZero
    private final Integer damage;
    @NotNull
    private final Ratio ratio;
    @NotNull
    @PositiveOrZero
    private final Integer cooldown;
    @NotNull
    @PositiveOrZero
    private final Integer lvl;
    @NotNull
    @PositiveOrZero
    private final Integer lvlMax;
    @NotNull
    private final Rank rank;
}
