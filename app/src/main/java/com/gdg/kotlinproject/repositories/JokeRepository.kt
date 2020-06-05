package com.gdg.kotlinproject.repositories

import com.gdg.kotlinproject.models.Joke
import com.gdg.kotlinproject.services.ChuckNorrisApiService
import com.gdg.kotlinproject.services.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object JokeRepository {

    private val service: ChuckNorrisApiService = ServiceProvider.jokeServiceObject
    private var categoryCache:List<String>? = null

    /**
     * This function uses withContext to make sure that
     * the api call is made on IO context, so even if this function is called from Dispatchers.Main
     * it will call api on Dispatchers.Main and return value to
     * called parent coroutine
     *
     */
    suspend fun getJokeCategories(): List<String>? {
        return categoryCache ?: kotlin.run {
            withContext(Dispatchers.IO) {
                categoryCache = service.getJokeCategories()
                categoryCache
            }
        }
    }

    /**
     * This function uses withContext to make sure that
     * the api call is made on IO context, and fetches a random joke from server
     *
     */
    suspend fun getRandomJoke(): Joke {
        return withContext(Dispatchers.IO) {
            service.getRandomJoke()
        }
    }

    /**
     * will return a single joke for the give category
     *
     */
    suspend fun getRandomJokeForCategory(category: String): Joke? {
        return withContext(Dispatchers.IO) {
            service.getRandomJokeOfCategory(category)
        }
    }
}