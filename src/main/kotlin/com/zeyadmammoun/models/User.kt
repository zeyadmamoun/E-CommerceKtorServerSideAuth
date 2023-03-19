package com.zeyadmammoun.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
) {
    fun hashPassword(): String {
        return BCrypt.hashpw(password,BCrypt.gensalt())
    }
}
