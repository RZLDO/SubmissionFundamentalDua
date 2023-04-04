package com.example.submissiondua.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiondua.data.githubModel.DetailResponse
import com.example.submissiondua.data.repository.GithubRepository

class DetailUsersViewModel:ViewModel() {
    private val detailRepository = GithubRepository()
    private val _detailUsers = MutableLiveData<DetailResponse>()

    val detailUsers:LiveData<DetailResponse>
        get() = _detailUsers

    private var _isLoading = detailRepository.isLoading
    val isLoading:LiveData<Boolean>
        get() = _isLoading

    fun getDetail(username : String){
        detailRepository.getDetail(username).observeForever{
            _detailUsers.value = it
        }
    }
}