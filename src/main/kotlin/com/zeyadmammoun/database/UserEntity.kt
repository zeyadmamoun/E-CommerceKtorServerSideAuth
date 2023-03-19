package com.zeyadmammoun.database

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity : Table<Nothing>("users") {
    val id = int("userid").primaryKey()
    val firstName = varchar("first_name")
    val lastName = varchar("last_name")
    val email = varchar("user_email")
    val password = varchar("user_password")
}