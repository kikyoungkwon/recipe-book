package com.kikyoung.recipe.feature

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseKoinTest
import com.kikyoung.recipe.feature.meal.MealScreen
import com.kikyoung.recipe.feature.meal.MealViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.test.mock.declare

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseKoinTest() {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, false)

    private val showScreenLiveData = MutableLiveData<MealScreen>()
    private val loadingLiveData = MutableLiveData<Boolean>()

    @Before
    fun before() {
        val mealViewModel = mockk<MealViewModel>(relaxed = true)
        every { mealViewModel.showScreenLiveData() } returns showScreenLiveData
        every { mealViewModel.loadingLiveData() } returns loadingLiveData
        declare { viewModel(override = true) { mealViewModel } }
        activityRule.launchActivity(null)
    }

    @Test
    fun `when it needs to show the details screen, it should show it`() {
        showScreenLiveData.postValue(MealScreen.DETAILS)
        onView(withId(R.id.mealDetailsFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun `when it needs to show loading, it should show a loading bar`() {
        loadingLiveData.postValue(true)
        onView(withId(R.id.progressBarViewGroup)).check(matches(isDisplayed()))
    }

    @Test
    fun `when it needs to hide loading, it should hide a loading bar`() {
        loadingLiveData.postValue(false)
        onView(withId(R.id.progressBarViewGroup)).check(matches(not(isDisplayed())))
    }
}