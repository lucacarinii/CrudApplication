package com.crudappbe.DB

import org.ktorm.database.Database

object DatabseConncetion {
    val database = Database.Companion.connect(
        "jdbc:mysql://localhost:3306/employeecrud",
        "com.mysql.cj.jdbc.Driver",
        "root",
        "123654Ee"
    )
}