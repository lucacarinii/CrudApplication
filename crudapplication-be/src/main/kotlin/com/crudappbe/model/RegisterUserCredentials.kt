package com.crudappbe.model

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class RegisterUserCredentials (val name: String,
                            val surname: String,
                            val password: String,
                            val email: String,
                            val idRole: Int ) {
     fun registerCredentials(): Boolean {
         return name.isNotEmpty() &&
                 surname.isNotEmpty() &&
                 password.isNotEmpty() &&
                 password.length >= 6 &&
                 email.isNotEmpty()
     }

    fun hashPassword(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}