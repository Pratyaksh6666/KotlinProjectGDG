package com.gdg.kotlinproject.services

import com.gdg.kotlinproject.models.Joke
import retrofit2.http.GET
import retrofit2.http.Query


interface ChuckNorrisApiService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): Joke

    @GET("jokes/categories")
    suspend fun getJokeCategories(): List<String>

    @GET("jokes/random")
    suspend fun getRandomJokeOfCategory(@Query("category") category: String): Joke
}