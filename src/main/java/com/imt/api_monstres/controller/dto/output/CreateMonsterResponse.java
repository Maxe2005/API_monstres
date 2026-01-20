package com.imt.api_monstres.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMonsterResponse {

    private String monsterId;
    private String message;
}
