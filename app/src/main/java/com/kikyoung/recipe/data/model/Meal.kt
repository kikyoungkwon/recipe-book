package com.kikyoung.recipe.data.model

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal")
    val id: Int,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strCategory")
    val category: String,
    @SerializedName("strArea")
    val area: String,
    @SerializedName("strInstructions")
    val instructions: String,
    @SerializedName("strMealThumb")
    val thumbImageUrl: String
)