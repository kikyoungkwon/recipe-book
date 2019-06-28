package com.kikyoung.recipe.data.mapper

import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.model.MealSearchResponse
import org.junit.Test
import kotlin.test.assertEquals

class MealMapperTest {

    private var mealMapper = MealMapper()

    @Test
    fun `it should map the meal list correctly`() {
        val response = getMealSearchResponse()
        val mealList = mealMapper.toMealList(response)
        assertEquals(response.mealResponses?.size, mealList?.size)
        assertEquals(response.mealResponses?.get(0), mealList?.get(0))
    }

    private fun getMealSearchResponse(): MealSearchResponse {
        return MealSearchResponse(
            listOf(
                Meal(
                    1, "name1", "category1", "area1",
                    "instructions1", "http://thumb.image1.url/"
                ),
                Meal(
                    2, "name2", "category2", "area2",
                    "instructions2", "http://thumb.image2.url/"
                )
            )
        )
    }
}