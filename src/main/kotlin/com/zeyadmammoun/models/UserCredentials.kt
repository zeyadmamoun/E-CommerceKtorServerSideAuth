package com.zeyadmammoun.models

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val email: String,
    val password: String
)
