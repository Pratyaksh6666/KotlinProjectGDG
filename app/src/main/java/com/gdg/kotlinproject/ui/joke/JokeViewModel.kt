package com.gdg.kotlinproject.ui.joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdg.kotlinproject.models.Joke
import com.gdg.kotlinproject.repositories.JokeRepository
import com.gdg.kotlinproject.viewmodel.ErrorState
import com.gdg.kotlinproject.viewmodel.LoadingState
import com.gdg.kotlinproject.viewmodel.State
import com.gdg.kotlinproject.viewmodel.SuccessState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class JokeViewModel: ViewModel() {
    private val repo: JokeRepository by lazy { JokeRepository }
    private val stateLiveData = MutableLiveData<State>()
    private val jokeOfCategory = MutableLiveData<Joke>()

    fun getJokeLiveData(): LiveData<Joke> = jokeOfCategory
    fun getStateLiveData(): LiveData<State> = stateLiveData

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        stateLiveData.value = ErrorState(throwable.localizedMessage)
    }

    fun onViewReady(jokeCategory: String? = null) {
        jokeCategory?.let { category ->
            fetchJokeForCategory(category)
        } ?: run {
            stateLiveData.value = ErrorState("Joke category is NULL")
        }
    }

    private fun fetchJokeForCategory(category: String) {
        viewModelScope.launch(errorHandler) {
            stateLiveData.value = LoadingState
            repo.getRandomJokeForCategory(category)?.let {
                stateLiveData.value = SuccessState
                jokeOfCategory.value = it
            }
        }
    }
}


