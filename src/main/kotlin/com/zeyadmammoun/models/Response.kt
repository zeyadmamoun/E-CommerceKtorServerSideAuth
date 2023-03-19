package com.zeyadmammoun.models

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success: Boolean,
    val data: String
)