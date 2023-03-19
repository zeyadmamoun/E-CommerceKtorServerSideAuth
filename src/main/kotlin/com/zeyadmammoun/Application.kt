package com.zeyadmammoun

import com.zeyadmammoun.database.UsersDatabase
import com.zeyadmammoun.plugins.configureMonitoring
import com.zeyadmammoun.plugins.configureRouting
import com.zeyadmammoun.plugins.configureSecurity
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.ktorm.database.Database

lateinit var database: Database

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {

    install(ContentNegotiation){
        json()
    }

    database = UsersDatabase.getDatabase()

    configureMonitoring()
    configureSecurity()
    configureRouting()
}
