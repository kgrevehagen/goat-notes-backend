package com.kgrevehagen.goatnotes.auth

import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockJwtSecurityContextFactory : WithSecurityContextFactory<WithMockJwt> {

    override fun createSecurityContext(annotation: WithMockJwt): SecurityContext {
        val mockJwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .subject(annotation.subject)
            .build()

        val authorities = annotation.roles
            .map { role ->
                "ROLE_$role"
            }.let { roles ->
                AuthorityUtils.createAuthorityList(roles)
            }

        val token = JwtAuthenticationToken(mockJwt, authorities)
        return SecurityContextHolder.createEmptyContext().apply {
            authentication = token
        }
    }
}
