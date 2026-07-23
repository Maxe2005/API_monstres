package com.imt.api_monstres.controller.dto.input;

import java.util.List;

import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonsterHttpDto {
    @NotBlank
    private final String name;
    @NotNull
    private final Elementary element;
    @NotNull
    @Valid
    private final StatsDto stats;
    @NotNull
    private final Rank rank;
    private final String cardDescription;
    private final String imageUrl;
    @NotEmpty
    @Valid
    private final List<SkillHttpDto> skills;

    @Getter
    @RequiredArgsConstructor
    public static class StatsDto {
        @NotNull
        @PositiveOrZero
        private final Integer hp;
        @NotNull
        @PositiveOrZero
        private final Integer atk;
        @NotNull
        @PositiveOrZero
        private final Integer def;
        @NotNull
        @PositiveOrZero
        private final Integer vit;
    }
}
