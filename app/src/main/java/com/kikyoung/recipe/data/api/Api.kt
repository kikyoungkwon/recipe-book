package com.kikyoung.recipe.data.api

import com.kikyoung.recipe.data.model.MealSearchResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("search.php")
    suspend fun search(@Query("s") term: String): Response<MealSearchResponse>
}