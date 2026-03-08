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
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.api_monstres.Repository.dto.SkillMongoDto;
import com.imt.api_monstres.utils.Rank;
import com.imt.api_monstres.utils.Ratio;
import com.imt.api_monstres.utils.Stat;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    Ratio ratio;

    @InjectMocks
    SkillService skillService;

    @BeforeEach
    void setup() {
        ratio = new Ratio(Stat.ATK, 0.5);
    }

    @Test
    void testCreateSkill() {
        String expectedId = "generated-id";
        // capture the argument to inspect its fields
        ArgumentCaptor<SkillMongoDto> captor = ArgumentCaptor.forClass(SkillMongoDto.class);
        when(skillRepository.save(any(SkillMongoDto.class))).thenReturn(expectedId);

        String returned = skillService.createSkill("monster1", 1, 10, ratio, 5, 1, 10, Rank.COMMON);

        assertEquals(expectedId, returned);
        verify(skillRepository).save(captor.capture());

        SkillMongoDto saved = captor.getValue();
        assertEquals("monster1", saved.getMonsterId());
        assertEquals(Integer.valueOf(1), saved.getNumber());
        assertEquals(Integer.valueOf(10), saved.getDamage());
        assertSame(ratio, saved.getRatio());
        assertEquals(Integer.valueOf(5), saved.getCooldown());
        assertEquals(Rank.COMMON, saved.getRank());
    }

    @Test
    void testDeleteSkill_exists() {
        when(skillRepository.findSkillById("skill1"))
            .thenReturn(Optional.of(new SkillMongoDto("skill1", "monster", 1, 1, ratio, 1, 1, 1, Rank.COMMON)));

        skillService.deleteSkill("skill1");

        verify(skillRepository).delete("skill1");
    }

    @Test
    void testDeleteSkill_notFound() {
        when(skillRepository.findSkillById("missing")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> skillService.deleteSkill("missing"));
        assertTrue(ex.getMessage().contains("Skill introuvable"));
        verify(skillRepository).findSkillById("missing");
        verify(skillRepository, never()).delete(any());
    }

    @Test
    void testGetAllSkillsByMonsterId() {
        List<SkillMongoDto> list = Collections.singletonList(
            new SkillMongoDto("s1", "monster", 1, 1, ratio, 1, 1, 1, Rank.COMMON));
        when(skillRepository.findAllByMonsterId("monster")).thenReturn(list);

        List<SkillMongoDto> result = skillService.getAllSkillsByMonsterId("monster");
        assertEquals(1, result.size());
        assertSame(list, result);
    }

    @Test
    void testGetSkillById_found() {
        SkillMongoDto dto = new SkillMongoDto("s1", "monster", 1, 1, ratio, 1, 1, 1, Rank.COMMON);
        when(skillRepository.findSkillById("s1")).thenReturn(Optional.of(dto));

        SkillMongoDto result = skillService.getSkillById("s1");
        assertEquals(dto, result);
    }

    @Test
    void testGetSkillById_notFound() {
        when(skillRepository.findSkillById("x")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> skillService.getSkillById("x"));
    }

    @Test
    void testUpdateSkill_partial() {
        SkillMongoDto existing = new SkillMongoDto("s1", "monster", 2, 3, ratio, 4, 5, 6, Rank.RARE);
        when(skillRepository.findSkillById("s1")).thenReturn(Optional.of(existing));

        skillService.updateSkill("s1", null, 10, null, null, null, null, null);

        verify(skillRepository).update(argThat(updated ->
            updated.getSkillId().equals("s1") &&
            updated.getDamage().equals(10) &&
            updated.getNumber().equals(2)
        ));
    }

    @Test
    void testUpdateSkill_full() {
        SkillMongoDto existing = new SkillMongoDto("s1", "monster", 2, 3, ratio, 4, 5, 6, Rank.RARE);
        when(skillRepository.findSkillById("s1")).thenReturn(Optional.of(existing));

        skillService.updateSkill("s1", 9, 10, ratio, 7, 8, 9, Rank.EPIC);

        verify(skillRepository).update(argThat(updated ->
            updated.getNumber().equals(9) &&
            updated.getDamage().equals(10) &&
            updated.getRank() == Rank.EPIC
        ));
    }

    @Test
    void testUpdateSkill_notFound() {
        when(skillRepository.findSkillById("missing")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> skillService.updateSkill("missing", 1, 1, ratio, 1, 1, 1, Rank.COMMON));
        verify(skillRepository).findSkillById("missing");
        verify(skillRepository, never()).update(any());
    }
}