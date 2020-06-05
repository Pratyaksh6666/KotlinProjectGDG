package com.gdg.kotlinproject.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gdg.kotlinproject.R
import com.gdg.kotlinproject.databinding.ActivityMainBinding
import com.gdg.kotlinproject.ui.base.BaseActivity
import com.gdg.kotlinproject.ui.joke.JokeActivity
import com.gdg.kotlinproject.viewmodel.ErrorState
import com.gdg.kotlinproject.viewmodel.HomeViewModel
import com.gdg.kotlinproject.viewmodel.LoadingState
import com.gdg.kotlinproject.viewmodel.SuccessState

@SuppressLint("Registered")
class HomeActivity : BaseActivity<ActivityMainBinding, HomeViewModel>(),
    JokeCategoriesAdapter.InteractionListener {
    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initObservers()
        viewModel.getJokeCategories()
    }

    private fun initObservers() {
        viewModel.getCategoriesLiveData().observe(this, Observer {
            it?.let { categories ->
                binding.rvJokeCategories.setData(categories)
            }
        })
        viewModel.getStateLiveData().observe(this, Observer { state ->
            when (state) {
                is LoadingState -> binding.progressCircular.show()
                is SuccessState -> binding.progressCircular.hide()
                is ErrorState -> {
                    binding.progressCircular.hide()
                    showSnackBar(state.errorMsg ?: getString(R.string.error_msg))
                }
            }
        })
    }

    private fun initUI() {
        with(binding.rvJokeCategories) {
            layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            adapter = JokeCategoriesAdapter(this@HomeActivity)
        }
    }

    private fun RecyclerView.setData(categories: List<String>) {
        (adapter as JokeCategoriesAdapter).submitList(categories)
    }

    override fun onCategorySelected(position: Int, category: String) {
        val intent = JokeActivity.getLaunchIntent(this, category)
        startActivity(intent)
    }
}