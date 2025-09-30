package com.kgrevehagen.goatnotes.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatchers

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
internal class SecurityConfiguration(
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    @Value("\${oauth.logout-url}")
    private val logoutUrl: String
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
            .exceptionHandling {
                it.defaultAuthenticationEntryPointFor(
                    customAuthenticationEntryPoint,
                    RequestMatchers.not { httpServletRequest ->
                        httpServletRequest.servletPath.startsWith("/ui")
                    })
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
            .logout { it.logoutSuccessUrl(logoutUrl) }
            .build()
    }
}