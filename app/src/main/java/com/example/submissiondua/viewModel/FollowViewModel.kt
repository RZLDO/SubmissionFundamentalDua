package com.example.submissiondua.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiondua.data.githubModel.FollowResponseItem
import com.example.submissiondua.data.repository.GithubRepository

class FollowViewModel: ViewModel() {
    private val repository =  GithubRepository()
    private val _follow = MutableLiveData<List<FollowResponseItem>>()
    val users : LiveData<List<FollowResponseItem>>
        get() = _follow

    private val _isloading = repository.isLoading
    val isLoading : LiveData<Boolean>
        get() = _isloading

    fun getFollow(username: String){
        repository.getFollowData(username).observeForever{
            _follow.value = it
        }
    }

    fun getFollowing(username: String){
        repository.getFollowing(username).observeForever{
            _follow.value = it
        }
    }
}