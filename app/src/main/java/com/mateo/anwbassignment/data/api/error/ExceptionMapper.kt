package com.mateo.anwbassignment.data.api.error

import android.net.ParseException
import com.mateo.anwbassignment.data.api.model.ErrorResponse
import com.mateo.anwbassignment.domain.core.AssignmentExceptions
import com.mateo.anwbassignment.domain.core.NetworkException
import com.mateo.anwbassignment.domain.core.ServerException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ExceptionMapper {
    fun mapException(exception: Exception): AssignmentExceptions
}

class ExceptionMapperImpl(
    private val moshiBuilder: Moshi.Builder
) : ExceptionMapper {

    override fun mapException(exception: Exception): AssignmentExceptions {
        return when (exception) {
            is UnknownHostException -> NetworkException()
            is SocketTimeoutException -> NetworkException()
            is ParseException -> ServerException()
            is HttpException -> {
                exception.response()?.errorBody()?.string()?.let { errorBody ->
                    try {
                        moshiBuilder.build().adapter(ErrorResponse::class.java).fromJson(errorBody)
                            ?.let {
                                return AssignmentExceptions()
                            }
                    } catch (jsonDataException: JsonDataException) {
                        return ServerException()
                    } catch (exception: Exception) {
                        return ServerException()
                    }
                }
                ServerException()
            }
            else -> ServerException()
        }
    }
}