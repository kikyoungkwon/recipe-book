package com.kikyoung.recipe.di

import com.kikyoung.recipe.data.repository.MealRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule = module {
    single { MealRepository(get(), get(), get(named("io"))) }
}