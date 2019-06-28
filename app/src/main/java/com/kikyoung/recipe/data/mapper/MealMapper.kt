package com.kikyoung.recipe.data.mapper

import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.model.MealSearchResponse

class MealMapper {

    fun toMealList(mealSearchResponse: MealSearchResponse): List<Meal>? {
        return mealSearchResponse.mealResponses
    }
}