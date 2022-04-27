package com.crudappbe.model

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class LoginUserCredentials (val email: String,
                                    val password: String ) {
    fun loginCredentials(): Boolean {
        return password.length >= 6 &&
                email.isNotEmpty()
    }

}