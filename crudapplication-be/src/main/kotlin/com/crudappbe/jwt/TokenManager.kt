package com.crudappbe.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.crudappbe.model.Employee
import io.ktor.server.config.HoconApplicationConfig
import java.util.*

    class TokenManager (val config: HoconApplicationConfig) {

    val audience = config.property("audience").getString()
    val secret = config.property("secret").getString()
    val issuer = config.property("issuer").getString()
    val expirationDate = System.currentTimeMillis() + 600000

    fun generateJwTToken(user: Employee): String {
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withExpiresAt(Date(expirationDate))
            .withClaim("email", user.email)
            .withClaim("emplId", user.emplId)
            .sign(Algorithm.HMAC256(secret))

        return token
    }

    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }

}