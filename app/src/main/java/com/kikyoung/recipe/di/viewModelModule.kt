package com.kikyoung.recipe.di

import com.kikyoung.recipe.feature.meal.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MealViewModel(get(), get(named("ui"))) }
}