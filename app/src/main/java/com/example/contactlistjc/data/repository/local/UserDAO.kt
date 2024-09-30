package com.example.contactlistjc.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserDB>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserDB)
}