package com.example.contactlistjc.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)

data class UserDB(
    @PrimaryKey val id: String,
    val avatar: String,
    val name: String,
    val lastName: String,
    val company: String,
    val number: String,
)
