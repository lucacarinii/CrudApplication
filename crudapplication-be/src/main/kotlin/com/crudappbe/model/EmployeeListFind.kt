package com.crudappbe.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeListFind(val emplId: Int,
                            val name: String,
                            val surname: String,
                            val email: String,
                            val idRole: Int)
