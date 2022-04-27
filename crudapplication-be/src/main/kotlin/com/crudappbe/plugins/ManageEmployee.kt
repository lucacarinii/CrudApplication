package com.crudappbe.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

fun Application.manageEmployeeRoutes() {
    routing {
        get("/employees") {}
        get("/employees/{id}") {}
        put("/employees/{id}") {}
        delete("/employees/{id}") {}
    }
}