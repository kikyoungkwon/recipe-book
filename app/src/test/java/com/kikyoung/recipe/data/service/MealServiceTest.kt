package com.kikyoung.recipe.data.service

import com.google.gson.Gson
import com.kikyoung.recipe.data.api.Api
import com.kikyoung.recipe.data.model.MealSearchResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MealServiceTest {

    private var apiService: Api = mockk()
    @ExperimentalCoroutinesApi
    private val ioDispatcher = Dispatchers.Unconfined
    private lateinit var mealService: MealService

    @Before
    fun before() {
        mealService = MealService(Gson(), apiService, ioDispatcher)
    }

    @Test
    fun `when searching meals is successful, it should return the result`() = runBlocking {
        val response = mockk<Response<MealSearchResponse>>()
        val responseBody = mockk<MealSearchResponse>()
        every { response.isSuccessful } returns true
        every { response.body() } returns responseBody
        coEvery { apiService.search(any()) } returns response
        TestCase.assertEquals(mealService.search("pasta"), responseBody)
    }

    @Test(expected = Exception::class)
    fun `when searching meals is unsuccessful, it should throw an exception`() = runBlocking<Unit> {
        val response = mockk<Response<MealSearchResponse>>()
        every { response.isSuccessful } returns false
        every { response.code() } returns 500
        every { response.errorBody()?.string() } returns "{\"message\":\"message\"}"
        coEvery { apiService.search(any()) } returns response
        mealService.search("pasta")
    }
}