package com.imt.api_monstres.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.imt.api_monstres.Repository.MonsterRepository;
import com.imt.api_monstres.Repository.SkillRepository;
import com.imt.commonsecurity.devauth.DevAutoAuthFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Preuve bout-en-bout (Story 1.1, finding de revue) que le câblage {@code
 * @Import(DevAutoAuthConfig.class)} + {@code @Profile("dev-local")} enregistre réellement {@link
 * DevAutoAuthFilter} dans un vrai {@link ApplicationContext} Spring quand le profil est actif —
 * exactement le "piège de câblage" que la story identifiait elle-même et que seule une
 * vérification manuelle (curl) avait validé jusqu'ici.
 */
@SpringBootTest(properties = "spring.data.mongodb.auto-index-creation=false")
@ActiveProfiles("dev-local")
class DevAutoAuthWiringActiveProfileTest {

    @MockBean
    private MonsterRepository monsterRepository;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void devAutoAuthFilterIsRegistered_whenDevLocalProfileActive() {
        FilterRegistrationBean<?> registration =
                applicationContext.getBean("devAutoAuthFilter", FilterRegistrationBean.class);

        assertThat(registration.getFilter()).isInstanceOf(DevAutoAuthFilter.class);
    }
}
