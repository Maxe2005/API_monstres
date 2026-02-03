package com.imt.api_monstres.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.imt.api_monstres.controller.dto.input.CreateMonsterRequest;
import com.imt.api_monstres.controller.dto.output.CreateMonsterResponse;
import com.imt.api_monstres.service.MonsterService;

@RestController
@RequestMapping("/api/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private static final Logger logger = LoggerFactory.getLogger(MonsterController.class);
    private final MonsterService monsterService;
    private final ObjectMapper objectMapper;

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

    /**
     * Get multiple monsters by their IDs
     *
     * @param monsterIds the list of monster IDs (comma-separated)
     * @return the monsters data
     */
    @GetMapping(params = "ids")
    public ResponseEntity<?> getMonstersByIds(@RequestParam(name = "ids") java.util.List<String> monsterIds) {
        var missingIds = monsterService.getMissingMonsterIds(monsterIds);
        if (!missingIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun monstre trouvé pour les IDs: " + String.join(", ", missingIds));
        }

        java.util.List<JsonNode> monsters = new java.util.ArrayList<>();
        for (String monsterId : monsterIds) {
            String json = monsterService.getMonsterJsonById(monsterId)
                    .orElseThrow(() -> new IllegalStateException("Monstre manquant après validation: " + monsterId));
            try {
                monsters.add(objectMapper.readTree(json));
            } catch (Exception exception) {
                throw new IllegalStateException("JSON invalide pour le monstre: " + monsterId, exception);
            }
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(monsters);
    }

}
