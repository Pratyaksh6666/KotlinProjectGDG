package com.gdg.kotlinproject.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceProvider {

    private const val baseUrl = "https://api.chucknorris.io/"

    //here we are creating instance of retrofit by using "by lazy"

     private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jokeServiceObject: ChuckNorrisApiService by lazy {
        retrofitInstance.create(ChuckNorrisApiService::class.java)
    }


}