package com.crudappbe

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.crudappbe.plugins.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        authRoutes()
        configureRouting()
    }.start(wait = true)
}
