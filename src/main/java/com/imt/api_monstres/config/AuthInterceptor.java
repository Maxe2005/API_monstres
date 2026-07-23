package com.imt.api_monstres.config;

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

    public AuthInterceptor(
            @Value("${gatcha.auth-api.host}") String authApiHost,
            @Value("${gatcha.auth-api.port}") String authApiPort) {
        super(new TokenVerificationClient(new RestTemplate(), authApiHost, authApiPort));
    }
}
