package com.crudappbe.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TokensBlacklistEntity: Table<Nothing>("tokensBlacklist") {
    val id = int("id").primaryKey()
    val token = varchar("token")
    val refreshToken = varchar("refreshToken")
    val email = varchar("email")
}