package com.example.contactlistjc.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)

data class UserDB(
    val avatar: String,
    @PrimaryKey val name: String,
    val lastName: String,
    val address: String,
    val phoneNumber: String,
)
