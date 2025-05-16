package com.kgrevehagen.goatnotes.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
internal class SecurityConfiguration(
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests(
                {
                    it.requestMatchers("/").permitAll()
                        .anyRequest()
                        .authenticated()
                }
            )
            .exceptionHandling { it.authenticationEntryPoint(customAuthenticationEntryPoint) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .build()
    }
}