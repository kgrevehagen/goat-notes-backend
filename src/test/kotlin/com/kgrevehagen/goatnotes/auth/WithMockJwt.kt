package com.kgrevehagen.goatnotes.auth

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@WithSecurityContext(factory = WithMockJwtSecurityContextFactory::class)
annotation class WithMockJwt(val subject: String = "user-id-123", val roles: Array<String> = ["USER"])
