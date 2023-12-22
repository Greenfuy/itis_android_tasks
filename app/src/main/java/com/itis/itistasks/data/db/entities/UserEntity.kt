package com.itis.itistasks.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val deletedDate: Long?
)