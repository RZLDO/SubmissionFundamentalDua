package com.example.submissiondua.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissiondua.data.githubModel.*
import com.example.submissiondua.data.remote.retrofit.ApiConfig
import com.example.submissiondua.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository(){
    private val apiService = ApiConfig.ApiService()
    private val _isLoading = MutableLiveData<Boolean>()
    companion object {
        private const val TAG = "GithubRepository"
    }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getUsers(username : String): LiveData<List<ItemsItem>>{
        _isLoading.value = true

        val liveData = MutableLiveData<List<ItemsItem>>()
        apiService.findUsers(username).enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    liveData.value = response.body()?.items as List
                }else{
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure : ${t.message}")
            }
        })
        return liveData
    }

    fun getDetail(username: String):LiveData<DetailResponse>{
        _isLoading.value = true
        val detailData = MutableLiveData<DetailResponse>()

        apiService.detailUser(username).enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    detailData.value = response.body()
                }else{
                    Log.d("Detail", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure Detail : ${t.message}")
            }
        })
        return detailData
    }

    fun getFollowData(username: String): LiveData<List<FollowResponseItem>>{
        _isLoading.value = true
        val followData = MutableLiveData<List<FollowResponseItem>>()

        apiService.follow(username).enqueue(object :Callback<List<FollowResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    followData.value = response.body()
                    Log.d("Followers", response.body().toString())
                }else{
                    Log.d("GetFollowData", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure getFollowData : ${t.message}")
            }
        })

        return followData
    }

    fun getFollowing(username:String):LiveData<List<FollowResponseItem>>{
        _isLoading.value = true
        val followingData = MutableLiveData<List<FollowResponseItem>>()

        apiService.following(username).enqueue(object : Callback<List<FollowResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    Log.d("Following", response.body().toString())
                    followingData.value = response.body()
                }else{
                    Log.d("GetFollowing","onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d("GetFollowing", "onFailure getFollowing : ${t.message}")
            }
        })

        return followingData
    }
}