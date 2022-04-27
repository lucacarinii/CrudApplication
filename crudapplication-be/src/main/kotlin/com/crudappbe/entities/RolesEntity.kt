package com.crudappbe.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object RolesEntity: Table<Nothing>("roles") {
    val idRole = int("idRole").primaryKey()
    val roleDesc = varchar("roleDesc")
}