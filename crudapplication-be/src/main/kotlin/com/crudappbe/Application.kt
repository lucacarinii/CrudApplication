package com.crudappbe

import com.crudappbe.jwt.TokenManager
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.crudappbe.plugins.*
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.CORS

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        val config = HoconApplicationConfig(ConfigFactory.load())
        val tokenManger = TokenManager(config)

        install(Authentication) {
            jwt {
                verifier(tokenManger.verifyJWTToken())
                realm = config.property("realm").getString()
                validate { jwtCredential ->
                    if(jwtCredential.payload.getClaim("email").asString().isNotEmpty()) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }
        }
        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowCredentials = true
            allowSameOrigin = true
            allowHost("localhost:3000", listOf("http", "https"))
        }
        install(ContentNegotiation) {
            json()
        }
        authRoutes()
        manageEmployeeRoutes()
        configureRouting()
    }.start(wait = true)
}
