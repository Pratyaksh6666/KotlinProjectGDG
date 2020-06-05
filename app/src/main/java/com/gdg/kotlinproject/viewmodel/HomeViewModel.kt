package com.gdg.kotlinproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdg.kotlinproject.repositories.JokeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repo: JokeRepository by lazy { JokeRepository }
    private val categoriesLiveData = MutableLiveData<List<String>>()
    private val stateLiveData = MutableLiveData<State>()

    fun getCategoriesLiveData(): LiveData<List<String>> = categoriesLiveData
    fun getStateLiveData(): LiveData<State> = stateLiveData

    fun getJokeCategories() {
        viewModelScope.launch {

            stateLiveData.value = LoadingState
            try {
                val categories = repo.getJokeCategories()

                stateLiveData.value = SuccessState
                categoriesLiveData.value = categories
            } catch (ex: Exception) {

                stateLiveData.value = ErrorState(ex.localizedMessage)
                }
        }
    }

}

sealed class State
object LoadingState : State()
object SuccessState : State()
class ErrorState(val errorMsg: String?) : State()