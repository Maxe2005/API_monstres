package com.imt.api_monstres.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;
import com.imt.api_monstres.utils.Stat;

@ExtendWith(MockitoExtension.class)
public class MonsterServiceTest {
    @Mock
    MonsterRepository monsterRepository;

    @Mock
    SkillService skillService;
    
    SkillHttpDto skillHttpDto;
    List<SkillHttpDto> skillHttpDtos;
    
    @InjectMocks
    MonsterService monsterService;

    @BeforeEach
    void setup() {
        skillHttpDto = new SkillHttpDto( 1, 10, new Ratio(Stat.ATK, 0.5), 5, 1, 10, Rank.COMMON);
        skillHttpDtos = List.of(skillHttpDto);
    }

    @Test
    void testCreateMonster() {
        String expectedId = "generated-id";
        ArgumentCaptor <MonsterMongoDto> captor = ArgumentCaptor.forClass(MonsterMongoDto.class);
        when(monsterRepository.save(any(MonsterMongoDto.class))).thenReturn("generated-id");

        String returned = monsterService.createMonster("player1", Elementary.FIRE, 10, 5, 100, 100, skillHttpDtos, Rank.COMMON);
        
        assertEquals(expectedId, returned);
        verify(monsterRepository).save(captor.capture());

        MonsterMongoDto saved = captor.getValue();
        assertEquals("player1", saved.getPlayerId());
        assertEquals(Elementary.FIRE, saved.getElement());
        assertEquals(Integer.valueOf(10), saved.getHp());
        assertEquals(Integer.valueOf(5), saved.getAtk());
        assertEquals(Integer.valueOf(100), saved.getDef());
        assertEquals(Integer.valueOf(100), saved.getVit());
    }

    @Test
    void testDeleteMonster_exists() {
        MonsterMongoDto dto = new MonsterMongoDto("m1", "player", Elementary.WATER, 1, 2, 3, 4, List.of("s1"), Rank.COMMON);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(dto));
        SkillMongoDto skill = new SkillMongoDto("s1", "m1", 1, 1, new Ratio(Stat.ATK,0.5), 1,1,1, Rank.COMMON);
        when(skillService.getAllSkillsByMonsterId("m1")).thenReturn(List.of(skill));

        monsterService.deleteMonster("m1");

        verify(skillService).deleteSkill("s1");
        verify(monsterRepository).delete("m1");
    }

    @Test
    void testDeleteMonster_notFound() {
        when(monsterRepository.findMonsterById("missing")).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> monsterService.deleteMonster("missing"));
        assertTrue(ex.getMessage().contains("Monstre introuvable"));
        verify(monsterRepository).findMonsterById("missing");
        verify(monsterRepository, never()).delete(any());
    }

    @Test
    void testGetAllMonstersByPlayerId() {
        List<MonsterMongoDto> list = Collections.singletonList(
            new MonsterMongoDto("m1", "player", Elementary.WATER, 1,1,1,1, null, Rank.COMMON));
        when(monsterRepository.findAllByPlayerId("player")).thenReturn(list);

        List<MonsterMongoDto> result = monsterService.getAllMonstersByPlayerId("player");
        assertEquals(1, result.size());
        assertSame(list, result);
    }

    @Test
    void testGetMonsterById_found() {
        MonsterMongoDto dto = new MonsterMongoDto("m1", "player", Elementary.WATER, 1,1,1,1, null, Rank.COMMON);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(dto));

        MonsterMongoDto result = monsterService.getMonsterById("m1");
        assertEquals(dto, result);
    }

    @Test
    void testGetMonsterById_notFound() {
        when(monsterRepository.findMonsterById("x")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> monsterService.getMonsterById("x"));
    }

    @Test
    void testUpdateMonster_partial() {
        MonsterMongoDto existing = new MonsterMongoDto("m1", "player", Elementary.WIND, 5, 6, 7, 8, List.of("sOld"), Rank.RARE);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(existing));

        monsterService.updateMonster("m1", null, null, 10, 11, null, null, null, null);

        ArgumentCaptor<MonsterMongoDto> captor = ArgumentCaptor.forClass(MonsterMongoDto.class);
        verify(monsterRepository).update(captor.capture());
        MonsterMongoDto updated = captor.getValue();
        assertEquals("m1", updated.getMonsterId());
        assertEquals(Integer.valueOf(10), updated.getHp());
        assertEquals(Integer.valueOf(11), updated.getAtk());
        assertEquals(Elementary.WIND, updated.getElement());
        assertEquals(existing.getSkills(), updated.getSkills());
        verify(skillService, never()).getAllSkillsByMonsterId(any());
    }

    @Test
    void testUpdateMonster_replaceSkills() {
        MonsterMongoDto existing = new MonsterMongoDto("m1", "player", Elementary.WIND, 5, 6, 7, 8, List.of("sOld"), Rank.RARE);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(existing));
        SkillMongoDto oldSkill = new SkillMongoDto("sOld", "m1", 1, 1, new Ratio(Stat.ATK,0.5), 1,1,1, Rank.COMMON);
        when(skillService.getAllSkillsByMonsterId("m1")).thenReturn(List.of(oldSkill));
        SkillHttpDto newSkill = new SkillHttpDto(2, 20, new Ratio(Stat.DEF, 0.2), 2, 2, 20, Rank.EPIC);
        when(skillService.createSkill(any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn("sNew");

        monsterService.updateMonster("m1", null, null, null, null, null, null, List.of(newSkill), null);

        verify(skillService).deleteSkill("sOld");
        ArgumentCaptor<MonsterMongoDto> captor = ArgumentCaptor.forClass(MonsterMongoDto.class);
        verify(monsterRepository).update(captor.capture());
        MonsterMongoDto updated = captor.getValue();
        assertEquals(List.of("sNew"), updated.getSkills());
    }

    @Test
    void testUpdateMonster_notFound() {
        when(monsterRepository.findMonsterById("missing")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> monsterService.updateMonster("missing", null, null, null, null, null, null, null, null));
        verify(monsterRepository).findMonsterById("missing");
        verify(monsterRepository, never()).update(any());
    }
}
