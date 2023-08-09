package com.zeyadmammoun.database

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity : Table<Nothing>("users") {
    val id = int("user_id").primaryKey()
    val firstName = varchar("first_name")
    val lastName = varchar("last_name")
    val email = varchar("email")
    val password = varchar("password")
}