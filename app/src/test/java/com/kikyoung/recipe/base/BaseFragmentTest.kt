package com.kikyoung.recipe.base

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kikyoung.recipe.R
import com.kikyoung.recipe.data.exception.NetworkException
import com.kikyoung.recipe.data.exception.ServerException
import com.kikyoung.recipe.feature.meal.MealListFragment
import com.kikyoung.recipe.feature.meal.MealViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.test.mock.declare

@RunWith(AndroidJUnit4::class)
class BaseFragmentTest : BaseKoinTest() {

    private var serverErrorLiveData = MutableLiveData<ServerException>()
    private var networkErrorLiveData = MutableLiveData<NetworkException>()

    @Before
    fun before() {
        val mealViewModel = mockk<MealViewModel>(relaxed = true)
        every { mealViewModel.serverErrorLiveData() } returns serverErrorLiveData
        every { mealViewModel.networkErrorLiveData() } returns networkErrorLiveData
        declare { viewModel(override = true) { mealViewModel } }
        launchFragmentInContainer<MealListFragment>()
    }

    @Test
    fun `when a server error is occurred, it should show a sever error message`() {
        serverErrorLiveData.postValue(ServerException("message"))
        onView(withText(R.string.common_error_server)).check(matches(isDisplayed()))
    }

    @Test
    fun `when a network error is occurred, it should show a network error message`() {
        networkErrorLiveData.postValue(NetworkException("message"))
        onView(withText(R.string.common_error_network)).check(matches(isDisplayed()))
    }
}