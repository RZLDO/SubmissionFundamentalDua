package com.example.submissiondua.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiondua.data.githubModel.ItemsItem
import com.example.submissiondua.data.repository.GithubRepository

class MainActivityViewModel:ViewModel() {
    private val repository = GithubRepository()

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>>
        get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getUsers(username: String) {
        repository.getUsers(username).observeForever { response ->
            _users.value = response
        }
        repository.isLoading.observeForever{
            _isLoading.value = it
        }
    }
}