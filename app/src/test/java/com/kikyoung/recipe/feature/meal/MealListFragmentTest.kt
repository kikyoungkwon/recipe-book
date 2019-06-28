package com.kikyoung.recipe.feature.meal

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseKoinTest
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.test.util.actionOnItemViewAtPosition
import com.kikyoung.recipe.test.util.withRecyclerView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.test.mock.declare
import org.robolectric.annotation.TextLayoutMode

@RunWith(AndroidJUnit4::class)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class MealListFragmentTest : BaseKoinTest() {

    private val mealsLiveData = MutableLiveData<List<Meal>?>()
    private val mealViewModel = mockk<MealViewModel>(relaxed = true)

    @Before
    fun before() {
        every { mealViewModel.mealsLiveData() } returns mealsLiveData
        declare { viewModel(override = true) { mealViewModel } }
        launchFragmentInContainer<MealListFragment>()
    }

    @Test
    fun `when it starts, it should only show the instruction`() {
        onView(withId(R.id.instructionTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.mealListRecyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.emptyTextView)).check(matches(not(isDisplayed())))
    }

    // Empty activity for FragmentScenario is not AppCompatActivity,
    // so search feature is tested on UI tests.

    @Test
    fun `when it is provided with some meals, it should show them on list`() {
        val meals = getMeals()
        mealsLiveData.postValue(meals)
        onView(withId(R.id.mealListRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTextView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.instructionTextView)).check(matches(not(isDisplayed())))

        testRecyclerViewItem(meals, 0)
        testRecyclerViewItem(meals, 1)
    }

    private fun testRecyclerViewItem(meals: List<Meal>, index: Int) {
        onView(withRecyclerView(R.id.mealListRecyclerView).atPositionOnView(index, R.id.nameTextView))
            .check(matches(withText(meals[index].name)))
        onView(withRecyclerView(R.id.mealListRecyclerView).atPositionOnView(index, R.id.categoryTextView))
            .check(matches(withText(meals[index].category)))
        onView(withRecyclerView(R.id.mealListRecyclerView).atPositionOnView(index, R.id.areaTextView))
            .check(matches(withText(meals[index].area)))
    }

    @Test
    fun `when it is provided with empty meals, it should show the empty message`() {
        mealsLiveData.postValue(listOf())
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.mealListRecyclerView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.instructionTextView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `when one of meals is clicked, it should show details of it`() {
        val index = 0
        val meals = getMeals()
        mealsLiveData.postValue(meals)
        onView(withId(R.id.mealListRecyclerView)).perform(actionOnItemViewAtPosition(index, R.id.nameTextView, click()))
        verify(exactly = 1) { mealViewModel.showDetails(meals[index]) }
    }

    private fun getMeals(): List<Meal> {
        return listOf(
            Meal(
                1, "name1", "category1", "area1",
                "instructions1", "http://thumb.image1.url/"
            ),
            Meal(
                2, "name2", "category2", "area2",
                "instructions2", "http://thumb.image2.url/"
            )
        )
    }
}