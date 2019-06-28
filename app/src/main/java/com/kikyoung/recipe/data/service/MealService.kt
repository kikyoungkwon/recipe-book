package com.kikyoung.recipe.data.service

import com.google.gson.Gson
import com.kikyoung.recipe.data.api.Api
import com.kikyoung.recipe.data.model.MealSearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MealService(
    gson: Gson,
    private val api: Api,
    private val ioDispatcher: CoroutineDispatcher
) : BaseService(gson, ioDispatcher) {

    suspend fun search(term: String): MealSearchResponse = withContext(ioDispatcher) {
        execute { api.search(term) }
    }
}