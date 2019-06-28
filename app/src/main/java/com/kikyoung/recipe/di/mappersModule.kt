package com.kikyoung.recipe.di

import com.kikyoung.recipe.data.mapper.MealMapper
import org.koin.dsl.module

val mappersModule = module {
    single { MealMapper() }
}