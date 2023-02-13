package com.crudappbe.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.crudappbe.DB.DatabaseConncetion
import com.crudappbe.entities.EmployeesEntity
import com.crudappbe.model.Employee
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import java.util.*

    class TokenManager (private val config: HoconApplicationConfig) {

        private val audience = config.property("audience").getString()
        private val secret = config.property("secret").getString()
        private val issuer = config.property("issuer").getString()
        private val expirationDate = System.currentTimeMillis() + 600000

        fun generateJwTToken(user: Employee): String {
            return JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withExpiresAt(Date(expirationDate + 600000))
                .withClaim("email", user.email)
                .withClaim("emplId", user.emplId)
                .sign(Algorithm.HMAC256(secret))
        }

        fun verifyJWTToken(): JWTVerifier {
            return JWT.require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
        }

        fun generateRefreshToken(): String {
            return UUID.randomUUID().toString();
        }

}