package com.imt.api_monstres.controller.dto.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CreateMonsterOutputDto {
    private final String monsterId;
    private final String message;
}
