package com.imt.api_monstres.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class MonsterService {
    private static int COUNT_ID;

    private static final Map<String, String> ID_TO_MONSTER_NAME = Map.of(
            "1", "abyssal-hydra",
            "2", "aether-primus",
            "3", "aquaspherе",
            "4", "bourrasque-kahn",
            "5", "ignis-vult",
            "6", "leviathan-rex",
            "7", "lithe-ondine",
            "8", "magma-drakon",
            "9", "pyrolosse",
            "10", "zephirion");

    public synchronized String generateNewMonsterId() {
        COUNT_ID += 1;
        if (COUNT_ID > ID_TO_MONSTER_NAME.size()) {
            COUNT_ID = 1;
        }
        return String.valueOf(COUNT_ID);
    }

    public Optional<String> getMonsterJsonById(String monsterId) {
        String normalizedId = monsterId == null ? null : monsterId.trim();
        String monsterName = normalizedId == null ? null : ID_TO_MONSTER_NAME.get(normalizedId);
        if (monsterName == null) {
            return Optional.empty();
        }

        String resourcePath = "com/imt/api_monstres/service/" + monsterName + ".json";
        ClassPathResource resource = new ClassPathResource(resourcePath);
        if (!resource.exists()) {
            return Optional.empty();
        }

        try (InputStream inputStream = resource.getInputStream()) {
            return Optional.of(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException exception) {
            throw new UncheckedIOException("Impossible de lire le fichier JSON du monstre: " + monsterName, exception);
        }
    }

    public Set<String> getMissingMonsterIds(List<String> monsterIds) {
        if (monsterIds == null || monsterIds.isEmpty()) {
            return Set.of();
        }

        return monsterIds.stream()
                .map(id -> id == null ? null : id.trim())
                .filter(id -> id == null || !ID_TO_MONSTER_NAME.containsKey(id))
                .collect(java.util.stream.Collectors.toSet());
    }
}
