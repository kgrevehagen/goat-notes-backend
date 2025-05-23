package com.kgrevehagen.goatnotes.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val bearerTokenAuthenticationEntryPoint: BearerTokenAuthenticationEntryPoint
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        bearerTokenAuthenticationEntryPoint.commence(request, response, authException)

        response.writer.write(
            """
            {
                "error": "Unauthorized",
                "message": "${authException.message}"
            }
            """.trimIndent()
        )
    }
}