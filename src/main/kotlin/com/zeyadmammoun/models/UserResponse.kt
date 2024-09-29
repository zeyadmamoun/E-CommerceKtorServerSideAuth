package com.zeyadmammoun.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: Boolean,
    val userData: User?,
    val errorMessage: String
)
