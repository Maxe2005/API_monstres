package com.imt.api_monstres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.imt.commonsecurity.auth.AbstractAuthInterceptor;
import com.imt.commonsecurity.auth.TokenVerificationClient;

/**
 * Vérifie la validité du token auprès de l'API d'Authentification, via gatcha-common-security.
 */
@Component
public class AuthInterceptor extends AbstractAuthInterceptor {

    // @Autowired requis : deux constructeurs existent (celui-ci + celui testable ci-dessous), donc
    // Spring ne peut plus déduire seul lequel utiliser pour l'injection du @Component.
    @Autowired
    public AuthInterceptor(
            @Value("${gatcha.auth-api.host}") String authApiHost,
            @Value("${gatcha.auth-api.port}") String authApiPort) {
        this(new RestTemplate(), authApiHost, authApiPort);
    }

    // Constructeur package-private avec RestTemplate injectable — permet de mocker l'appel HTTP vers
    // API_authentification dans les tests (voir DevAutoAuthFlowIntegrationTest, Story 1.1). Le
    // constructeur public ci-dessus continue d'utiliser un RestTemplate réel : comportement de
    // production inchangé.
    AuthInterceptor(RestTemplate restTemplate, String authApiHost, String authApiPort) {
        super(new TokenVerificationClient(restTemplate, authApiHost, authApiPort));
    }
}
