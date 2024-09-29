package com.example.contactlistjc.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
}