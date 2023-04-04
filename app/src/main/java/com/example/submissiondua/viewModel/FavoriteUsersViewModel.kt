package com.example.submissiondua.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiondua.data.githubModel.DetailResponse
import com.example.submissiondua.data.remote.room.database.FavoriteUsers
import com.example.submissiondua.data.repository.FavoriteRepository

class FavoriteUsersViewModel(application: Application):ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)
    private val _favoriteUsers = MutableLiveData<FavoriteUsers>()
    fun insert(favoriteUsers: FavoriteUsers) {
        mFavoriteRepository.insert(favoriteUsers)
    }

    fun delete(favoriteUsers: FavoriteUsers){
        mFavoriteRepository.delete(favoriteUsers)
    }

    fun getUsersByUsername(username:String):LiveData<FavoriteUsers> = mFavoriteRepository.getUserByUsername(username)
}