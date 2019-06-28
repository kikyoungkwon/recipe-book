package com.kikyoung.recipe.base

import android.app.Application
import com.kikyoung.recipe.BuildConfig
import com.kikyoung.recipe.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setKoin()
    }

    private fun setKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    coroutinesModule,
                    networkModule,
                    servicesModule,
                    mappersModule,
                    repositoriesModule,
                    viewModelModule
                )
            )
        }
    }
}