package com.kikyoung.recipe.data.model

import com.google.gson.annotations.SerializedName

data class MealSearchResponse(
    @SerializedName("meals")
    val mealResponses: List<Meal>?
)