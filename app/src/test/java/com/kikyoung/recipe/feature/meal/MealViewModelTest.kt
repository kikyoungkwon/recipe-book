package com.kikyoung.recipe.feature.meal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kikyoung.recipe.data.exception.ServerException
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.repository.MealRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MealViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val searchTerm = "spaghetti"

    private var mealRepository: MealRepository = mockk(relaxed = true)
    private val uiDispatcher = Dispatchers.Unconfined
    private var viewModel = MealViewModel(mealRepository, uiDispatcher)

    @Test
    fun `when searching meals, it should show and hide the loading bar`() {
        coEvery { mealRepository.search(searchTerm) } returns mockk()
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.loadingLiveData().observeForever(observer)
        viewModel.searchMeals(searchTerm)
        verifySequence {
            observer.onChanged(true)
            observer.onChanged(false)
        }
    }

    @Test
    fun `when searching meals, it should start searching meals`() {
        viewModel.searchMeals(searchTerm)
        coVerify { mealRepository.search(searchTerm) }
    }

    @Test
    fun `when searching meals is successful, it should provide the result`() {
        val meals = mockk<List<Meal>>()
        coEvery { mealRepository.search(searchTerm) } returns meals
        val observer = mockk<Observer<List<Meal>?>>()
        viewModel.mealsLiveData().observeForever(observer)
        viewModel.searchMeals(searchTerm)
        verify(exactly = 1) {
            observer.onChanged(meals)
        }
    }

    @Test
    fun `when searching meals is unsuccessful, it should show the error`() {
        val serverException = mockk<ServerException>()
        coEvery { mealRepository.search(searchTerm) } throws serverException
        val observer = mockk<Observer<ServerException>>()
        viewModel.serverErrorLiveData().observeForever(observer)
        viewModel.searchMeals(searchTerm)
        verify(exactly = 1) {
            observer.onChanged(serverException)
        }
    }
}