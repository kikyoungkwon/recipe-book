package com.kikyoung.recipe.data.repository

import com.kikyoung.recipe.data.exception.NetworkException
import com.kikyoung.recipe.data.mapper.MealMapper
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.model.MealSearchResponse
import com.kikyoung.recipe.data.service.MealService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MealRepositoryTest {

    private val searchTerm = "pasta"

    private val mealMapper = mockk<MealMapper>(relaxed = true)
    private val mealService = mockk<MealService>()
    @ExperimentalCoroutinesApi
    private val ioDispatcher = Dispatchers.Unconfined
    private lateinit var mealRepository: MealRepository

    @Before
    fun before() {
        mealRepository = MealRepository(mealMapper, mealService, ioDispatcher)
    }

    @Test
    fun `when searching meals is successful, it should map to meal list`() = runBlocking {
        val mealSearchResponse = mockk<MealSearchResponse>()
        coEvery { mealService.search(searchTerm) } returns mealSearchResponse
        mealRepository.search(searchTerm)
        verify { mealMapper.toMealList(mealSearchResponse) }
    }

    @Test
    fun `when searching meals is successful and mapped the response correctly, it should return the list of meal`() =
        runBlocking {
            val mealSearchResponse = mockk<MealSearchResponse>()
            coEvery { mealService.search(searchTerm) } returns mealSearchResponse
            val mealList = mockk<List<Meal>>()
            every { mealMapper.toMealList(mealSearchResponse) } returns mealList
            assertEquals(mealList, mealRepository.search(searchTerm))
        }

    @Test(expected = NetworkException::class)
    fun `when searching meals throws an exception, it should throw it`() = runBlocking<Unit> {
        val exception = NetworkException("network error")
        coEvery { mealService.search(searchTerm) } throws exception
        mealRepository.search(searchTerm)
    }
}