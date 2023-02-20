package com.crudappbe.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenBlacklist (val token: String, val refreshToken: String)