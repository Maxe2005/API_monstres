package com.imt.api_monstres.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imt.api_monstres.controller.dto.input.CreateMonsterRequest;
import com.imt.api_monstres.controller.dto.output.CreateMonsterResponse;
import com.imt.api_monstres.service.MonsterService;

@RestController
@RequestMapping("/api/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private static final Logger logger = LoggerFactory.getLogger(MonsterController.class);
    private final MonsterService monsterService;

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
                new CreateMonsterResponse(monsterService.generateNewMonsterId(), "Monster created successfully"));
    }

    /**
     * Get a monster by its ID
     * 
     * @param monsterId the ID of the monster
     * @return the monster data
     */
    @GetMapping("/{monsterId}")
    public ResponseEntity<?> getGlobalMonster(@PathVariable String monsterId) {
        return monsterService.getMonsterJsonById(monsterId)
                .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aucun monstre trouvé pour l'ID: " + monsterId));
    }

}
