package com.thomaskioko.stargazer.core.util

import com.squareup.moshi.Moshi
import retrofit2.HttpException

open class ExceptionHandler(
    val errorCode: Int = -1,
    val errorMessage: String,
) : Exception() {
    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage(): String {
        return errorMessage
    }

    companion object {
        fun parseException(e: HttpException): ExceptionHandler {

            val errorResponse = e.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            } ?: ErrorResponse(
                errorDescription = "Something went wrong!!"
            )

            return try {
                ExceptionHandler(e.code(), errorResponse.errorDescription)
            } catch (_: Exception) {
                ExceptionHandler(e.code(), "unexpected error!!ً")
            }
        }
    }
}


fun Throwable.resolveError() = when (this) {
    is HttpException -> {
        when (code()) {
            502 -> ExceptionHandler(code(), "Internal error!")
            401 -> ExceptionHandler(errorMessage = "Authentication error!")
            400 -> ExceptionHandler.parseException(this)
            else -> ExceptionHandler.parseException(this)
        }
    }
    else -> ExceptionHandler(errorMessage = "Something went wrong")
}

data class ErrorResponse(
    val errorDescription: String, // this is the translated error shown to the user directly from the API
    val causes: Map<String, String> = emptyMap() //this is for errors on specific field on a form
)
