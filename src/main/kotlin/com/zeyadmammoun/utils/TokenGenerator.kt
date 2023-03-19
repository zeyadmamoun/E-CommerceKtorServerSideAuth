package com.zeyadmammoun.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.zeyadmammoun.models.User
import io.ktor.server.config.*
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.net.PasswordAuthentication
import java.util.*

class TokenGenerator(config: HoconApplicationConfig) {

    private val secret = config.property("jwt.secret").getString()
    private val issuer = config.property("jwt.issuer").getList().toString()
    private val audience = config.property("jwt.audience").getString()
    val myRealm = config.property("jwt.realm").getString()
    private val now = Clock.System.now()
    private val expireDate = now.plus(30, DateTimeUnit.DAY, TimeZone.currentSystemDefault())

    fun generateToken(userEmail: String, password: String): String {
        println(expireDate)
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", userEmail)
            .withClaim("password", password)
            .withExpiresAt(Date.from(expireDate.toJavaInstant()))
            .sign(Algorithm.HMAC256(secret))
    }
}