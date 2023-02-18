package com.crudappbe.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val emplId: Int,
    val name: String,
    val surname: String,
    val email: String,
    val idRole: Int,
    val password: String,
    val isLogged: Boolean,
    val tokens: TokenResponse?
)