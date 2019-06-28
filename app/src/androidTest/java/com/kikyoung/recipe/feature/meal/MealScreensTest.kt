package com.kikyoung.recipe.feature.meal

import android.view.View.VISIBLE
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseScreenTest
import com.kikyoung.recipe.feature.MainActivity
import com.kikyoung.recipe.test.util.ViewVisibilityIdlingResource
import com.kikyoung.recipe.test.util.actionOnItemViewAtPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// NOTE vs BDD, vs Robot pattern
class MealScreensTest : BaseScreenTest() {

    companion object {
        private const val RESPONSE_FILE_SEARCH_SUCCESS = "success.json"
    }

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    override fun before() {
        super.before()
        activityRule.launchActivity(null)
    }

    @Test
    fun showMealListAndMealDetails() {
        mockWebServer.setSearchResponse(RESPONSE_FILE_SEARCH_SUCCESS)
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("a"), pressImeActionButton())
        val viewVisibilityIdlingResource = ViewVisibilityIdlingResource(
            activityRule.activity.findViewById<RecyclerView>(R.id.mealListRecyclerView),
            VISIBLE
        )
        IdlingRegistry.getInstance().register(viewVisibilityIdlingResource)
        onView(withId(R.id.mealListRecyclerView)).perform(actionOnItemViewAtPosition(0, R.id.nameTextView, click()))
        IdlingRegistry.getInstance().unregister(viewVisibilityIdlingResource)
        onView(withId(R.id.mealDetailsFragment)).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.mealListFragment)).check(matches(isDisplayed()))
    }
}