package com.crudappbe.plugins

import com.crudappbe.DB.DatabaseConncetion
import com.crudappbe.entities.EmployeesEntity
import com.crudappbe.model.Employee
import com.crudappbe.model.EmployeeRequest
import com.crudappbe.model.Response
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.ktorm.dsl.*

fun Application.manageEmployeeRoutes() {
    routing {

        val db = DatabaseConncetion(HoconApplicationConfig(ConfigFactory.load())).database

        authenticate {
            get("/employees") {
                val employees = db.from(EmployeesEntity).select()
                    .map {
                        Employee(it[EmployeesEntity.emplId] ?: -1,
                            it[EmployeesEntity.name] ?: "",
                            it[EmployeesEntity.surname] ?: "",
                            it[EmployeesEntity.email] ?: "",
                            it[EmployeesEntity.idRole] ?: -1, "")
                    }

                call.respond(HttpStatusCode.OK, Response(
                    result = true,
                    data = employees
                ))
            }
            get("/employees/{id}") {
                val emplId = call.parameters["id"]?.toInt() ?: -1

                val employee = db.from(EmployeesEntity).select()
                    .where {
                        EmployeesEntity.emplId eq emplId
                    }
                    .map {
                        Employee(it[EmployeesEntity.emplId] ?: -1,
                            it[EmployeesEntity.name] ?: "",
                            it[EmployeesEntity.surname] ?: "",
                            it[EmployeesEntity.email] ?: "",
                            it[EmployeesEntity.idRole] ?: -1, "")
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
        }
    }
}