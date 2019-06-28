package com.kikyoung.recipe.feature

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseActivity
import com.kikyoung.recipe.feature.meal.MealScreen
import com.kikyoung.recipe.feature.meal.MealViewModel
import com.kikyoung.recipe.util.extension.hide
import com.kikyoung.recipe.util.extension.observeChanges
import com.kikyoung.recipe.util.extension.show
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mealViewModel: MealViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationController = findNavController(this, R.id.navHostFragment)
        mealViewModel.showScreenLiveData().observeChanges(this) {
            // MealList is shown default.
            if (it == MealScreen.DETAILS) navigationController.navigate(R.id.mealDetails)
        }

        mealViewModel.loadingLiveData().observeChanges(this) { visible ->
            // NOTE vs Data Binding
            progressBarViewGroup.apply { if (visible) show() else hide() }
        }
    }
}
