package com.kikyoung.recipe.feature.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseFragment
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.util.extension.observeChanges
import kotlinx.android.synthetic.main.fragment_meal_details.*

class MealDetailsFragment : BaseFragment<MealViewModel>(MealViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBar()

        viewModel.selectedMealLiveData().observeChanges(this) {
            setViews(it)
        }
    }

    private fun setActionBar() {
        setHasOptionsMenu(true)
        try {
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.setSupportActionBar(mealDetailsToolbar)
            appCompatActivity.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setTitle(R.string.recipe_details_title)
            }
        } catch (e: ClassCastException) {
            // Ignore for FragmentScenario tests
        }
    }

    private fun setViews(meal: Meal) {
        // NOTE vs Data Binding
        nameTextView.text = meal.name
        categoryTextView.text = meal.category
        areaTextView.text = meal.area
        instructionsTextView.text = meal.instructions
        Glide.with(context!!)
            .load(meal.thumbImageUrl)
            .apply(RequestOptions.fitCenterTransform())
            .into(thumbImageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}