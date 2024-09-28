package com.zeyadmammoun.models

import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: Boolean,
    val userData: User
)
