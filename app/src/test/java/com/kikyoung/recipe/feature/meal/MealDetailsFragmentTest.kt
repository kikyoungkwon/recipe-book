package com.kikyoung.recipe.feature.meal

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseKoinTest
import com.kikyoung.recipe.data.model.Meal
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.test.mock.declare
import org.robolectric.annotation.TextLayoutMode

@RunWith(AndroidJUnit4::class)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class MealDetailsFragmentTest : BaseKoinTest() {

    private val selectedMealLiveData = MutableLiveData<Meal>()
    private val mealViewModel = mockk<MealViewModel>(relaxed = true)

    @Before
    fun before() {
        every { mealViewModel.selectedMealLiveData() } returns selectedMealLiveData
        declare { viewModel(override = true) { mealViewModel } }
        launchFragmentInContainer<MealDetailsFragment>()
    }

    @Test
    fun `when it is provided with a selected meal, it should show correctly`() {
        val meal = getMeal()
        selectedMealLiveData.postValue(meal)
        onView(withId(R.id.nameTextView)).check(matches(withText(meal.name)))
        onView(withId(R.id.categoryTextView)).check(matches(withText(meal.category)))
        onView(withId(R.id.areaTextView)).check(matches(withText(meal.area)))
        onView(withId(R.id.instructionsTextView)).check(matches(withText(meal.instructions)))
    }

    private fun getMeal(): Meal {
        return Meal(
            0, "name", "category", "area",
            "instructions", "http://thumb.image.url/"
        )
    }
}