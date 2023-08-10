package com.zeyadmammoun.plugins

import com.typesafe.config.ConfigFactory
import com.zeyadmammoun.database.DatabaseFunctions
import com.zeyadmammoun.models.Response
import com.zeyadmammoun.models.User
import com.zeyadmammoun.models.UserCredentials
import com.zeyadmammoun.utils.EmailValidation
import com.zeyadmammoun.utils.TokenGenerator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt

fun Application.configureRouting() {
    val tokenGenerator = TokenGenerator(HoconApplicationConfig(ConfigFactory.load()))
    val tools = DatabaseFunctions()

    routing {
        post("/register") {
            val user: User?
            try {
                user = call.receive()
            } catch (e: Exception) {
                println(e)
                call.respond(HttpStatusCode.NotAcceptable,Response(false,"data sent is not valid"))
                return@post
            }

            if (!EmailValidation.isEmailValid(user.email)){
                call.respond(HttpStatusCode.BadRequest,Response(false,"email is not valid"))
               return@post
            }
            if (tools.checkIfUserExist(user.email) != null){
                call.respond(HttpStatusCode.BadRequest,Response(false,"email already exist"))
                return@post
            }
            if (user.password.length < 6) {
                call.respond(HttpStatusCode.BadRequest, Response(false, "password is too short at least 6 digits"))
                return@post
            }
            tools.insetUser(user)
            val token = tokenGenerator.generateToken(user.email,user.password)
            call.respond(HttpStatusCode.OK,Response(true,token))
        }

        post("/login"){
            val userCredentials: UserCredentials?
            try {
                userCredentials = call.receive()
            } catch (e: Exception) {
                println(e)
                call.respond(HttpStatusCode.NotAcceptable,Response(false,"data sent is not valid"))
                return@post
            }

            val user = tools.checkIfUserExist(userCredentials.email)
            if (user == null){
                call.respond(HttpStatusCode.NotAcceptable,Response(false,"invalid username or password"))
                return@post
            }

            val checkPassword = BCrypt.checkpw(userCredentials.password,user.password)
            if (!checkPassword){
                call.respond(HttpStatusCode.NotAcceptable,Response(false,"invalid username or password"))
                return@post
            }
            val token = tokenGenerator.generateToken(userCredentials.email,userCredentials.password)
            call.respond(HttpStatusCode.OK,Response(true,token))
        }

        authenticate {
            get("/speedLogin"){
                try {
                    val principal = call.principal<JWTPrincipal>()
                    principal?.let {
                        val email = principal.payload.getClaim("email").asString()
                        val password = principal.payload.getClaim("password").asString()
                        val userCredentials = UserCredentials(email,password)
                        call.respond(Response(true,"User email: ${userCredentials.email} is verified"))
                    }
                }catch (e: Exception){
                    println(e)
                    call.respond(Response(false,"token needs to be regenerated"))
                }
            }
        }
    }
}

