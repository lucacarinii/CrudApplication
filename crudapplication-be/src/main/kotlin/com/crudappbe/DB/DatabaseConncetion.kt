package com.crudappbe.DB

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import org.ktorm.database.Database

class DatabaseConncetion(val config: HoconApplicationConfig) {

    val database = Database.Companion.connect(
        config.property("DB_URL").getString(),
        config.property("DB_DRIVER").getString(),
        config.property("DB_USER").getString(),
        config.property("DB_PSW").getString()
    )
}