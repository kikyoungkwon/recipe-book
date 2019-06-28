package com.kikyoung.recipe.data.repository

import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.service.MealService
import com.kikyoung.recipe.data.mapper.MealMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MealRepository(
    private val mealMapper: MealMapper,
    private val mealService: MealService,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun search(term: String): List<Meal>? = withContext(ioDispatcher) {
        // TODO Use Cache
        mealMapper.toMealList(mealService.search(term))
    }
}