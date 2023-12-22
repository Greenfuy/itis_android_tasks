package com.itis.itistasks.data.db.repositories

import com.itis.itistasks.data.db.entities.UserEntity
import com.itis.itistasks.data.db.entities.relations.UserFilmCrossRef
import com.itis.itistasks.data.db.entities.relations.UserWithFavoriteFilms
import com.itis.itistasks.data.model.FilmModel
import com.itis.itistasks.data.model.UserModel
import com.itis.itistasks.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {

    private val userDao = ServiceLocator.getDbInstance().getUserDao()

    suspend fun add(user: UserModel) : Boolean {
        return withContext(Dispatchers.IO) {
            if (userDao.checkPhoneExist(user.phone) || userDao.checkEmailExist(user.email)) {
                return@withContext false
            }
            val userEntity = UserEntity(
                name = user.name,
                email = user.email,
                phone = user.phone,
                password = user.password,
                deletedDate = user.deletedDate
            )
            userDao.add(userEntity)
            return@withContext true
        }
    }

    suspend fun delete(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
    }

    suspend fun setDeletionDate(id: Int, deletionDate: Long) {
        withContext(Dispatchers.IO) {
            userDao.setDeletionDate(id, deletionDate)
        }
    }

    suspend fun checkUserData(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.checkUserData(email, password)
        }
    }

    suspend fun getUserByData(email: String, password: String): UserEntity {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserByData(email, password)
        }
    }

    suspend fun getUserById(id: Int): UserEntity {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserById(id)
        }
    }

    suspend fun updatePhone(id: Int, phone: String) : Boolean {
        return withContext(Dispatchers.IO) {
            if (userDao.checkPhoneExist(phone)) {
                return@withContext false
            }
            userDao.updatePhone(id, phone)
            return@withContext true
        }
    }

    suspend fun updatePassword(id: Int, password: String) : Boolean {
        return withContext(Dispatchers.IO) {
            userDao.updatePassword(id, password)
            return@withContext true
        }
    }

    suspend fun getAllFavorites(id: Int): List<FilmModel> {
        return withContext(Dispatchers.IO) {
            val userFavoriteFilms = userDao.getAllFavorites(id)
            return@withContext userFavoriteFilms.films.map { filmEntity -> filmEntity.toModel() }
        }
    }

    suspend fun addFavorite(userId: Int, filmName: String) {
        withContext(Dispatchers.IO) {
            val filmEntity = ServiceLocator.getDbInstance().getFilmDao().getByName(filmName)
            getUserById(userId).let { userEntity ->
                filmEntity.let { filmEntity ->
                    userDao.addUserFilmCrossRef(
                        UserFilmCrossRef(userEntity.userId, filmEntity.filmId)
                    )
                }
            }
        }
    }

    suspend fun isFavorite(userId: Int, filmName: String): Boolean {
        val userFavouriteFilms: UserWithFavoriteFilms =
            userDao.getAllFavorites(userId)
        return userFavouriteFilms.films.any { filmEntity ->
            filmEntity.name == filmName
        }
    }


    suspend fun deleteFavorite(id: Int, filmName: String) {
        withContext(Dispatchers.IO) {
            val filmEntity = ServiceLocator.getDbInstance().getFilmDao().getByName(filmName)
            getUserById(id).let { userEntity ->
                filmEntity.let { filmEntity ->
                    userDao.deleteUserFilmCrossRef(
                        UserFilmCrossRef(userEntity.userId, filmEntity.filmId)
                    )
                }
            }
        }
    }
}