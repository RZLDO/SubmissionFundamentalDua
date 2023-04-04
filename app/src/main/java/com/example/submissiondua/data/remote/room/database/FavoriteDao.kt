package com.example.submissiondua.data.remote.room.database

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUsers: FavoriteUsers)

    @Update
    fun update(favoriteUsers: FavoriteUsers)

    @Delete
    fun delete(favoriteUsers: FavoriteUsers)

    @Query("Select * from favoriteUsers ORDER BY id ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("Select * from favoriteUsers where username = :username")
    fun getUsersByUsername(username:String) :LiveData<FavoriteUsers>
}