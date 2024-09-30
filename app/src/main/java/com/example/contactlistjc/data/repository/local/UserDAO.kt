package com.example.contactlistjc.data.repository.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserDB>>
}