package com.crudappbe.plugins

import com.crudappbe.DB.DatabaseConncetion
import com.crudappbe.entities.EmployeesEntity
import com.crudappbe.jwt.TokenManager
import com.crudappbe.model.*
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

fun Application.authRoutes() {

    val db = DatabaseConncetion(HoconApplicationConfig(ConfigFactory.load())).database
    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    routing {
        post("/register") {
            val userCreds = call.receive<RegisterUserCredentials>()

            if(!userCreds.registerCredentials()) {
                call.respond(HttpStatusCode.BadRequest, Response(
                    result = false,
                    data = "Creds not valid"
                ))
                return@post
            }

            val name = userCreds.name
            val surname = userCreds.surname
            val idRole = userCreds.idRole
            val email = userCreds.email
            val password = userCreds.hashPassword()

            val user = db.from(EmployeesEntity).select()
                .where {
                    EmployeesEntity.email eq email
                }.map {
                    it[EmployeesEntity.email]
                }.firstOrNull()

            if(user != null) {
                call.respond(HttpStatusCode.BadRequest,
                Response(
                    result = false,
                    data = "User already present"
                ))
                return@post
            }

            db.insert(EmployeesEntity) {
                set(it.name, name)
                set(it.surname, surname)
                set(it.idRole, idRole)
                set(it.email, email)
                set(it.password, password)
                set(it.isLogged, false)
                set(it.jwt, null)
                set(it.refreshToken, null)
            }

            call.respond(HttpStatusCode.OK, Response(
                result = true,
                data = "User created"
            ))
        }
        post("/login") {
            val userCreds = call.receive<LoginUserCredentials>()

            if(!userCreds.loginCredentials()) {
                call.respond(HttpStatusCode.BadRequest,
                Response(
                    result = false,
                    data = "bad creds"
                ))
                return@post
            }

            val email = userCreds.email
            val password = userCreds.password

            val user = db.from(EmployeesEntity).select()
                .where {
                    EmployeesEntity.email eq email
                }
                .map {
                    val emplId = it[EmployeesEntity.emplId]!!
                    val name = it[EmployeesEntity.name]!!
                    val surname = it[EmployeesEntity.surname]!!
                    val email = it[EmployeesEntity.email]!!
                    val password = it[EmployeesEntity.password]!!
                    val idRole = it[EmployeesEntity.idRole]!!
                    val isLogged = it[EmployeesEntity.isLogged]!!
                    val jwt = it[EmployeesEntity.jwt]!!
                    val refreshToken = it[EmployeesEntity.refreshToken]!!
                    Employee(emplId, name, surname, email, idRole, password, isLogged, TokenResponse(jwt, refreshToken))
                }.firstOrNull()

            if(user == null) {
                call.respond(HttpStatusCode.BadRequest, Response(
                    result = false,
                    data = "User not present"
                ))
                return@post
            }

            val checkPsw = BCrypt.checkpw(password, user.password)

            if(!checkPsw) {
                call.respond(HttpStatusCode.BadRequest, Response(
                    result = false,
                    data = "Bad creds"
                ))
                return@post
            }

            val token = tokenManager.generateJwTToken(user)
            val refreshToken = tokenManager.generateRefreshToken()
            val tokenResponse = TokenResponse(token, refreshToken)
            db.update(EmployeesEntity) {
                where { EmployeesEntity.emplId eq user.emplId }
                set(it.isLogged, true)
                set(it.jwt, token)
                set(it.refreshToken, refreshToken)
            }

            call.respond(HttpStatusCode.OK,
                Response(
                    result = true,
                    data = tokenResponse
                ))
        }
        post("/refresh") {
            val details = call.receive<TokenRequest>()

            if(details.refreshToken.isEmpty() || details.email.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Response(
                        result = false,
                        data = "Bad req"
                    )
                )
                return@post
            }

            val user = db.from(EmployeesEntity).select()
                .where {
                    EmployeesEntity.email eq details.email
                }
                .map {
                    val emplId = it[EmployeesEntity.emplId]!!
                    val name = it[EmployeesEntity.name]!!
                    val surname = it[EmployeesEntity.surname]!!
                    val email = it[EmployeesEntity.email]!!
                    val password = it[EmployeesEntity.password]!!
                    val idRole = it[EmployeesEntity.idRole]!!
                    val isLogged = it[EmployeesEntity.isLogged]!!
                    val jwt = it[EmployeesEntity.jwt]!!
                    val refreshToken = it[EmployeesEntity.refreshToken]!!
                    Employee(emplId, name, surname, email, idRole, password, isLogged, TokenResponse(jwt, refreshToken))
                }.firstOrNull()

            if(user == null) {
                call.respond(HttpStatusCode.BadRequest, Response(
                    result = false,
                    data = "User not present"
                ))
                return@post
            }

            if(user.tokens.refreshToken != details.refreshToken) {
                call.respond(HttpStatusCode.BadRequest, Response(
                    result = false,
                    data = "Bad refresh token"
                ))
                return@post
            }

            val token = tokenManager.generateJwTToken(user)
            val refreshToken = tokenManager.generateRefreshToken()
            val tokenResponse = TokenResponse(token, refreshToken)

            db.update(EmployeesEntity) {
                where { EmployeesEntity.emplId eq user.emplId }
                set(it.isLogged, true)
                set(it.jwt, token)
                set(it.refreshToken, refreshToken)
            }

            call.respond(HttpStatusCode.OK,
                Response(
                    result = true,
                    data = tokenResponse
                ))
        }
    }
}