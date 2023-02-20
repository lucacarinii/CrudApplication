package com.crudappbe.entities

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object EmployeesEntity: Table<Nothing>("employees") {
    val emplId = int("emplId").primaryKey()
    val name = varchar("name")
    val surname = varchar("surname")
    val email = varchar("email")
    val idRole = int("idRole")
    val password = varchar("password")
    val isLogged = boolean("isLogged")
    val jwt = varchar("jwt")
    val refreshToken = varchar("refreshToken")
}