package com.itis.itistasks.data.db.repositories

import com.itis.itistasks.data.db.entities.RateEntity
import com.itis.itistasks.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RateRepository {

    private val rateDao = ServiceLocator.getDbInstance().getRateDao()

    suspend fun add(rate : RateEntity) {
        withContext(Dispatchers.IO) {
            rateDao.add(rate)
        }
    }

    suspend fun get(filmId : Int, userId : Int) : RateEntity? {
        return withContext(Dispatchers.IO) {
            return@withContext rateDao.get(filmId, userId)
        }
    }

    suspend fun getAvg(filmId : Int) : Float {
        return withContext(Dispatchers.IO) {
            return@withContext rateDao.getAverageRate(filmId)
        }
    }
}