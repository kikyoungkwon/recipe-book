package com.kikyoung.recipe.di

import com.kikyoung.recipe.data.service.MealService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val servicesModule = module {
    single { MealService(get(), get(), get(named("io"))) }
}