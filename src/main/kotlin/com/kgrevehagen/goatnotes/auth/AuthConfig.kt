package com.kgrevehagen.goatnotes.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint

@Configuration
internal class AuthConfig {

    @Bean
    fun bearerAuthenticationEntryPoint(): BearerTokenAuthenticationEntryPoint {
        return BearerTokenAuthenticationEntryPoint()
    }
}