package com.imt.api_monstres.config;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;

/**
 * Contre-preuve du finding de revue Story 1.1 : sans profil Spring {@code dev-local} actif (le
 * cas par défaut — staging, prod, ou dev local sans opt-in explicite), {@code DevAutoAuthConfig}
 * ne fait jamais partie du contexte Spring et le filtre dev-only n'existe donc pas (AC #3).
 */
@SpringBootTest(properties = "spring.data.mongodb.auto-index-creation=false")
class DevAutoAuthWiringInactiveProfileTest {

    @MockBean
    private MonsterRepository monsterRepository;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void devAutoAuthFilterIsAbsent_whenDevLocalProfileNotActive() {
        assertThatThrownBy(() -> applicationContext.getBean("devAutoAuthFilter", FilterRegistrationBean.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
