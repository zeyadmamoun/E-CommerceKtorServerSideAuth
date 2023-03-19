package com.zeyadmammoun.database

import org.ktorm.database.Database

class UsersDatabase {

    companion object{
        private const val databaseUrl = "jdbc:postgresql://localhost:5432/e-commerce_users"
        @Volatile
        var INSTANCE: Database? = null

        fun getDatabase(): Database{
            return INSTANCE ?: synchronized(this){
                val instance = Database.connect(
                    url = databaseUrl,
                    driver = "org.postgresql.Driver",
                    user = "zeyadmamoun",
                    password = "172000"
                )
                INSTANCE = instance
                return instance
            }
        }
    }

}