package com.imt.api_monstres.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imt.api_monstres.controller.dto.input.CreateMonsterRequest;
import com.imt.api_monstres.controller.dto.output.CreateMonsterResponse;

@RestController
@RequestMapping("/api/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private static final Logger logger = LoggerFactory.getLogger(MonsterController.class);

    /**
     * Create a new monster
     * 
     * @param createMonsterRequest the monster data
     * @return the ID of the created monster
     */
    @PostMapping("create")
    public ResponseEntity<CreateMonsterResponse> createMonster(
            @Valid @RequestBody CreateMonsterRequest createMonsterRequest) {
        logger.info("Creating a new monster with data: {}", createMonsterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateMonsterResponse("monsterId-placeholder", "Monster created successfully"));
    }
}
