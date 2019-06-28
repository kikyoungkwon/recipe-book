package com.kikyoung.recipe.data.service

import com.google.gson.Gson
import com.kikyoung.recipe.data.exception.ServerException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseService(private val gson: Gson, private val ioDispatcher: CoroutineDispatcher) {

    protected suspend fun <T> execute(
        handleError: ((response: Response<T>) -> Unit)? = null,
        serviceCall: suspend () -> Response<T>
    ): T = withContext(ioDispatcher) {
        val response: Response<T> = serviceCall.invoke()
        if (!response.isSuccessful) {
            handleError?.invoke(response)
            throw gson.fromJson(response.errorBody()?.string(), ServerException::class.java)
        }
        response.body() ?: throw ServerException("empty response body")
    }
}