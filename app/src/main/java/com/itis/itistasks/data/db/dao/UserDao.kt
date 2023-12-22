package com.itis.itistasks.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.itis.itistasks.data.db.entities.UserEntity
import com.itis.itistasks.data.db.entities.relations.UserFilmCrossRef
import com.itis.itistasks.data.db.entities.relations.UserWithFavoriteFilms

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun add(user : UserEntity)

    @Query("SELECT * FROM users")
    fun getAll() : List<UserEntity>

    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserById(id : Int) : UserEntity

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    fun getUserByData(email : String, password : String) : UserEntity

    @Update
    fun update(user : UserEntity)

    @Delete
    fun delete(user : UserEntity)

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getAllFavorites(id: Int): UserWithFavoriteFilms

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE phone = :phone LIMIT 1)")
    fun checkPhoneExist(phone : String) : Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE email = :email LIMIT 1)")
    fun checkEmailExist(email : String) : Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE email = :email AND password = :password LIMIT 1)")
    fun checkUserData(email : String, password: String) : Boolean

    @Query("UPDATE users SET deletedDate = :deletedDate WHERE userId = :id")
    fun setDeletionDate(id: Int, deletedDate: Long)

    @Query("UPDATE users SET password = :password WHERE userId = :id")
    fun updatePassword(id: Int, password: String)

    @Query("UPDATE users SET phone = :phone WHERE userId = :id")
    fun updatePhone(id: Int, phone: String)

    @Insert(onConflict = REPLACE)
    fun addUserFilmCrossRef(userFilmCrossRef: UserFilmCrossRef)

    @Delete()
    suspend fun deleteUserFilmCrossRef(userFilmCrossRef: UserFilmCrossRef)

    @Query("SELECT * FROM users WHERE deletedDate != 0 AND deletedDate < :timeThreshold")
    fun getUsersForDeletion(timeThreshold: Long): List<UserEntity>?
}