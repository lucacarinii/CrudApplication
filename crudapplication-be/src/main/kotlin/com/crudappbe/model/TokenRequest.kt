package com.crudappbe.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest (val refreshToken: String, val email: String)