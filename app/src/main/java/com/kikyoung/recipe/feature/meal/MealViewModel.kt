package com.kikyoung.recipe.feature.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kikyoung.recipe.base.BaseViewModel
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.data.repository.MealRepository
import com.kikyoung.recipe.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealRepository: MealRepository,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val showScreenLiveData = SingleLiveEvent<MealScreen>()
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val mealsLiveData = MutableLiveData<List<Meal>?>()
    private val selectedMealLiveData = MutableLiveData<Meal>()

    fun searchMeals(term: String) {
        launch {
            loadingLiveData.postValue(true)
            try {
                // NOTE vs RxJava
                mealsLiveData.postValue(mealRepository.search(term))
            } catch (e: Exception) {
                // NOTE vs sealed class result
                handleRepositoryError(e)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }

    fun showDetails(meal: Meal) {
        selectedMealLiveData.postValue(meal)
        showScreenLiveData.postValue(MealScreen.DETAILS)
    }

    fun showScreenLiveData(): LiveData<MealScreen> = showScreenLiveData
    fun loadingLiveData(): LiveData<Boolean> = loadingLiveData
    fun mealsLiveData(): LiveData<List<Meal>?> = mealsLiveData
    fun selectedMealLiveData(): LiveData<Meal> = selectedMealLiveData
}