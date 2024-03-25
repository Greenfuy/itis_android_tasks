package com.itis.itistasks.data


import com.itis.itistasks.R
import com.itis.itistasks.utils.ResManager
import com.itis.itistasks.data.exceptions.TooManyRequestsException
import retrofit2.HttpException

class ExceptionHandlerDelegate(
    private val resManager: ResManager,
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
//                    401 -> {
//                        UserNotAuthorizedException(message = resManager.getString(R.string.user_not_authorized))
//                    }

                    403 -> {
                        ex
                    }

                    429 -> {
                        TooManyRequestsException(message = resManager.getString(R.string.too_many_requests))
                    }

                    else -> {
                        ex
                    }
                }
            }

            else -> {
                ex
            }
        }
    }
}