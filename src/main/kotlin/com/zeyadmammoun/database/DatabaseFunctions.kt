package com.zeyadmammoun.database

import com.zeyadmammoun.database
import com.zeyadmammoun.models.User
import org.ktorm.dsl.*

class DatabaseFunctions {

    fun insetUser(user: User) {
        database.insert(UserEntity) {
            set(it.firstName, user.firstname.lowercase())
            set(it.lastName, user.lastname.lowercase())
            set(it.email, user.email)
            set(it.password, user.hashPassword())
        }
    }

    fun getAllUsers(){
        val users = database.from(UserEntity).select().map {
            val firstname = it[UserEntity.firstName]!!
            val lastname = it[UserEntity.lastName]!!
            val email = it[UserEntity.email]!!
            val password = it[UserEntity.password]!!
            User(firstname,lastname,email,password)
        }
        println(users)
    }

    fun checkIfUserExist(userEmail: String): User? {
        val queryUser = database.from(UserEntity).select()
            .where { UserEntity.email eq userEmail }
            .map {
                val firstname = it[UserEntity.firstName]!!
                val lastname = it[UserEntity.lastName]!!
                val email = it[UserEntity.email]!!
                val password = it[UserEntity.password]!!
                User(firstname,lastname,email,password)
            }.firstOrNull()
        println(queryUser)
        return queryUser
    }
}