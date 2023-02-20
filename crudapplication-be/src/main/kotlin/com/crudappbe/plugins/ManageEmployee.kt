package com.crudappbe.plugins

import com.crudappbe.DB.DatabaseConncetion
import com.crudappbe.entities.EmployeesEntity
import com.crudappbe.entities.TokensBlacklistEntity
import com.crudappbe.model.EmployeeListFind
import com.crudappbe.model.EmployeeRequest
import com.crudappbe.model.Response
import com.crudappbe.model.TokenBlacklist
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.ktorm.dsl.*

fun Application.manageEmployeeRoutes() {
    routing {

        val db = DatabaseConncetion(HoconApplicationConfig(ConfigFactory.load())).database

        authenticate {

            intercept(ApplicationCallPipeline.Call) {
                val token = call.request.headers["Authorization"]?.replace("Bearer ", "")
                val tokensBlacklisted = db.from(TokensBlacklistEntity).select()
                    .map {
                        TokenBlacklist(
                            it[TokensBlacklistEntity.token] ?: "",
                            it[TokensBlacklistEntity.refreshToken] ?: ""
                        )
                    }
                tokensBlacklisted.forEach { tokenB -> if(tokenB.token.equals(token)) {
                    print("TOKEN: $token\nDB TOKEN: $tokenB\n")
                    return@intercept finish()
                }
                }
            }

            get("/employees") {
                val employees = db.from(EmployeesEntity).select()
                    .map {
                        EmployeeListFind(it[EmployeesEntity.emplId] ?: -1,
                            it[EmployeesEntity.name] ?: "",
                            it[EmployeesEntity.surname] ?: "",
                            it[EmployeesEntity.email] ?: "",
                            it[EmployeesEntity.idRole] ?: -1)
                    }

                call.respond(HttpStatusCode.OK, Response(
                    result = true,
                    data = employees
                ))
            }
            get("/employees/{id}") {
                print(call.parameters["id"])
                val emplId = call.parameters["id"]?.toInt() ?: -1

                val employee = db.from(EmployeesEntity).select()
                    .where {
                        EmployeesEntity.emplId eq emplId
                    }
                    .map {
                        EmployeeListFind(it[EmployeesEntity.emplId] ?: -1,
                            it[EmployeesEntity.name] ?: "",
                            it[EmployeesEntity.surname] ?: "",
                            it[EmployeesEntity.email] ?: "",
                            it[EmployeesEntity.idRole] ?: -1)
                    }.firstOrNull()

                if(employee == null) {
                    call.respond(HttpStatusCode.BadRequest, Response(
                        result = false,
                        data = "Employee not found"
                    ))
                }
                else {
                    call.respond(HttpStatusCode.OK, Response(
                        result = true,
                        data = employee
                    ))
                }
            }
            put("/employees/{id}") {
                val emplId = call.parameters["id"]?.toInt() ?: -1
                val updateEmployee = call.receive<EmployeeRequest>()

                val rowAffected = db.update(EmployeesEntity) {
                    set(it.name, updateEmployee.name)
                    set(it.surname, updateEmployee.surname)
                    set(it.email, updateEmployee.email)
                    set(it.idRole, updateEmployee.idRole)
                    where { it.emplId eq emplId }
                }

                if(rowAffected == 1) {
                    call.respond(HttpStatusCode.OK, Response(
                        result = true,
                        data = "Employee Updated"
                    ))
                }
                else {
                    call.respond(HttpStatusCode.BadRequest, Response(
                        result = false,
                        data = "Employee Not Updated"
                    ))
                }
            }
            delete("/employees/{id}") {
                val emplId = call.parameters["id"]?.toInt() ?: -1

                val rowAffected = db.delete(EmployeesEntity) {
                    it.emplId eq emplId
                }

                if(rowAffected == 1) {
                    call.respond(HttpStatusCode.OK, Response(
                        result = true,
                        data = "Employee deleted"
                    ))
                }
                else {
                    call.respond(HttpStatusCode.BadRequest, Response(
                        result = false,
                        data = "Employee Not deleted"
                    ))
                }
            }
            post("/employee") {

                val findEmployee = call.receive<EmployeeListFind>()

                val employees = db.from(EmployeesEntity).select()
                    .where {
                        (if(findEmployee.emplId == -1) EmployeesEntity.emplId like "%%"
                                else EmployeesEntity.emplId like "%" + findEmployee.emplId + "%") and
                                (EmployeesEntity.name like "%" + findEmployee.name + "%") and
                                (EmployeesEntity.surname like "%" + findEmployee.surname + "%") and
                                (EmployeesEntity.email like "%" + findEmployee.email + "%") and
                                (if(findEmployee.idRole == -1) EmployeesEntity.idRole like "%%"
                                        else EmployeesEntity.idRole like "%" + findEmployee.idRole + "%")
                    }
                    .map {
                        EmployeeListFind(it[EmployeesEntity.emplId] ?: -1,
                            it[EmployeesEntity.name] ?: "",
                            it[EmployeesEntity.surname] ?: "",
                            it[EmployeesEntity.email] ?: "",
                            it[EmployeesEntity.idRole] ?: -1)
                    }

                if(employees.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound, Response(
                        result = true,
                        data = "Not Found"
                    )
                    )
                }

                call.respond(HttpStatusCode.OK, Response(
                    result = true,
                    data = employees
                ))
            }
            get("/islogged/{email}") {
                val email = call.parameters["email"] ?: ""

                val user = db.from(EmployeesEntity).select()
                    .where { EmployeesEntity.email eq email }
                    .map {
                        it[EmployeesEntity.isLogged]
                    }.firstOrNull()

                if(user == null){
                    println("USER NOT PRESENT")
                    call.respond(HttpStatusCode.BadRequest, Response(
                        result = false,
                        data = "User not present"
                    ))
                }
                else {
                    println("USER PRESENT")
                    call.respond(HttpStatusCode.OK, Response(
                        result = true,
                        data = user
                    ))
                }
            }
        }
    }
}