package com.example.submissiondua.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissiondua.data.remote.room.database.FavoriteDao
import com.example.submissiondua.data.remote.room.database.FavoriteUsers
import com.example.submissiondua.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) :ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite():LiveData<List<FavoriteUsers>> = mFavoriteRepository.getAllFavorite()

}