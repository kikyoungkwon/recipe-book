package com.kikyoung.recipe.base

import android.os.AsyncTask
import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.kikyoung.recipe.data.TestMockWebServer
import kotlinx.coroutines.asCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.declare

open class BaseScreenTest : KoinTest {

    private val okHttp3IdlingResource = OkHttp3IdlingResource.create("OkHttp3", get())
    protected val mockWebServer = TestMockWebServer()

    @Before
    open fun before() {
        IdlingRegistry.getInstance().register(okHttp3IdlingResource)
        declare {
            single(named("io"), override = true) {
                AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
            }
            single(named("baseUrl"), override = true) {
                mockWebServer.getBaseUrl()
            }
        }
    }

    @After
    open fun after() {
        stopKoin()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }
}