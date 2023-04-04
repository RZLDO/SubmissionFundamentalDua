package com.example.submissiondua.data.remote.room.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUsers (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name="avatarUrl")
    var avatarUrl: String? = null
) : Parcelable {

}