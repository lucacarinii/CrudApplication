package com.crudappbe.model

import kotlinx.serialization.Serializable

@Serializable
data class Response<T> (val result: Boolean, val data: T)