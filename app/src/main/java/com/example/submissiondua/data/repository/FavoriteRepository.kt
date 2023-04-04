package com.example.submissiondua.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissiondua.data.remote.room.database.FavoriteDao
import com.example.submissiondua.data.remote.room.database.FavoriteRoomDatabase
import com.example.submissiondua.data.remote.room.database.FavoriteUsers
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application){
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUsers>> = mFavoriteDao.getAllFavoriteUsers()

    fun getUserByUsername(username:String): LiveData<FavoriteUsers> = mFavoriteDao.getUsersByUsername(username)

    fun insert(favoriteUsers: FavoriteUsers){
        executorService.execute {mFavoriteDao.insert(favoriteUsers)}
    }

    fun delete(favoriteUsers: FavoriteUsers){
        executorService.execute {mFavoriteDao.delete(favoriteUsers)}
    }

}