package com.example.submissiondua.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissiondua.data.remote.room.database.FavoriteUsers

class NoteDiffCallback (private val mOldFavoriteList: List<FavoriteUsers>, private val mNewFavoriteList: List<FavoriteUsers>):DiffUtil.Callback(){
    override fun getOldListSize(): Int{
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        val newEmployee = mNewFavoriteList[newItemPosition]

        return oldEmployee.username == newEmployee.username && oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}