package com.imt.api_monstres.config;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Vérifie la validité du token auprès de l'API d'Authentification, via gatcha-common-security.
 * Extrait et propage l'identifiant de correlation X-Request-Id.
 */
@Component
public class AuthInterceptor extends com.imt.commonsecurity.auth.AbstractAuthInterceptor {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthInterceptor.class);
    private static final String X_REQUEST_ID = "X-Request-Id";

    // @Autowired requis : deux constructeurs existent (celui-ci + celui testable ci-dessous), donc
    // Spring ne peut plus déduire seul lequel utiliser pour l'injection du @Component.
    @Autowired
    public AuthInterceptor(
            @Value("${gatcha.auth-api.host}") String authApiHost,
            @Value("${gatcha.auth-api.port}") String authApiPort) {
        super(new com.imt.commonsecurity.auth.TokenVerificationClient(
            new org.springframework.web.client.RestTemplate(), authApiHost, authApiPort));
    }

    // Constructeur package-private avec RestTemplate injectable — permet de mocker l'appel HTTP vers
    // API_authentification dans les tests (voir DevAutoAuthFlowIntegrationTest, Story 1.1). Le
    // constructeur public ci-dessus continue d'utiliser un RestTemplate réel : comportement de
    // production inchangé.
    AuthInterceptor(org.springframework.web.client.RestTemplate restTemplate,
            String authApiHost, String authApiPort) {
        super(new com.imt.commonsecurity.auth.TokenVerificationClient(restTemplate, authApiHost, authApiPort));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = request.getHeader(X_REQUEST_ID);
        if (requestId != null) {
            MDC.put("X-Request-Id", requestId);
            logger.debug("X-Request-Id extrait de la requête entrante : {}", requestId);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove("X-Request-Id");
        super.afterCompletion(request, response, handler, ex);
    }
}
