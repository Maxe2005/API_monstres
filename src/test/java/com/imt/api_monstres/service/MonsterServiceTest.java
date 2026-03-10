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
import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.MonsterMongoDto;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.controller.dto.output.MonsterOutputDto;
import com.imt.api_monstres.controller.dto.input.SkillHttpDto;
import com.imt.api_monstres.controller.dto.output.SkillOutputDto;
import com.imt.api_monstres.utils.Elementary;
import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;
import com.imt.api_monstres.utils.Stat;

@ExtendWith(MockitoExtension.class)
public class MonsterServiceTest {
    @Mock
    MonsterRepository monsterRepository;

    @Mock
    SkillRepository skillRepository;

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
        SkillOutputDto skill = new SkillOutputDto("s1", "m1", 1, 1, new Ratio(Stat.ATK,0.5), 1, 1, 1, Rank.COMMON);
        when(skillService.getAllSkillsByMonsterId("m1")).thenReturn(List.of(skill));
        when(skillRepository.findSkillById("s1")).thenReturn(Optional.of(new SkillMongoDto("s1","m1",1,1,new Ratio(Stat.ATK,0.5),1,1,1,Rank.COMMON)));

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

        // request without skills
        List<MonsterOutputDto> result = monsterService.getAllMonstersByPlayerId("player", false);
        assertEquals(1, result.size());
        MonsterOutputDto out = result.get(0);
        assertEquals("m1", out.getMonsterId());
        assertEquals("player", out.getPlayerId());
        assertEquals(Elementary.WATER, out.getElement());
        assertEquals(Integer.valueOf(1), out.getHp());
        assertEquals(Rank.COMMON, out.getRank());
        assertSame(null, out.getSkills());
    }

    @Test
    void testGetAllMonstersByPlayerId_withSkills() {
        MonsterMongoDto dto = new MonsterMongoDto("m1", "player", Elementary.WATER, 1,1,1,1, List.of("s1"), Rank.COMMON);
        when(monsterRepository.findAllByPlayerId("player")).thenReturn(List.of(dto));

        SkillOutputDto skill = new SkillOutputDto("s1", "m1", 1, 1, new Ratio(Stat.ATK,0.5), 1, 1, 1, Rank.COMMON);
        when(skillService.getAllSkillsByMonsterId("m1")).thenReturn(List.of(skill));

        List<MonsterOutputDto> result = monsterService.getAllMonstersByPlayerId("player", true);
        assertEquals(1, result.size());
        MonsterOutputDto out = result.get(0);
        assertEquals("m1", out.getMonsterId());
        assertEquals("player", out.getPlayerId());
        assertEquals(Elementary.WATER, out.getElement());
        assertEquals(Rank.COMMON, out.getRank());
        assertEquals(1, out.getSkills().size());
        SkillOutputDto outSkill = out.getSkills().get(0);
        assertEquals(skill.getSkillId(), outSkill.getSkillId());
    }

    @Test
    void testGetMonsterById_found() {
        MonsterMongoDto dto = new MonsterMongoDto("m1", "player", Elementary.WATER, 1,1,1,1, null, Rank.COMMON);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(dto));

        MonsterOutputDto result = monsterService.getMonsterById("m1", false);
        assertEquals(dto.getMonsterId(), result.getMonsterId());
        assertEquals(dto.getPlayerId(), result.getPlayerId());
        assertEquals(dto.getElement(), result.getElement());
        assertEquals(dto.getHp(), result.getHp());
        assertEquals(dto.getRank(), result.getRank());
        assertSame(null, result.getSkills());
    }

    @Test
    void testGetMonsterById_notFound() {
        when(monsterRepository.findMonsterById("x")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> monsterService.getMonsterById("x", false));
    }

    @Test
    void testGetMonsterById_withSkills() {
        // monster has a skill id list
        MonsterMongoDto dto = new MonsterMongoDto("m1", "player", Elementary.WATER, 1,1,1,1,
                List.of("s1"), Rank.COMMON);
        when(monsterRepository.findMonsterById("m1")).thenReturn(Optional.of(dto));

        SkillOutputDto skill = new SkillOutputDto("s1", "m1", 1, 1, new Ratio(Stat.ATK, 0.5), 1, 1, 1, Rank.COMMON);
        when(skillService.getAllSkillsByMonsterId("m1")).thenReturn(List.of(skill));

        MonsterOutputDto result = monsterService.getMonsterById("m1", true);
        assertEquals(dto.getMonsterId(), result.getMonsterId());
        assertEquals(dto.getPlayerId(), result.getPlayerId());
        assertEquals(dto.getElement(), result.getElement());
        assertEquals(dto.getHp(), result.getHp());
        assertEquals(dto.getRank(), result.getRank());
        assertEquals(1, result.getSkills().size());
        SkillOutputDto outSkill = result.getSkills().get(0);
        assertEquals(skill.getSkillId(), outSkill.getSkillId());
    }

}
