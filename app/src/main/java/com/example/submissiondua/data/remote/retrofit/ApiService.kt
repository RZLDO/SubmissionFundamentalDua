package com.example.submissiondua.data.remote.retrofit

import com.example.submissiondua.data.githubModel.DetailResponse
import com.example.submissiondua.data.githubModel.FollowResponse
import com.example.submissiondua.data.githubModel.FollowResponseItem
import com.example.submissiondua.data.githubModel.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUsers(@Query("q")username:String):Call<GithubResponse>

    @GET("users/{username}")
    fun detailUser(@Path("username") username: String):Call<DetailResponse>

    @GET("users/{username}/followers")
    fun follow(@Path("username") username: String): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun following(@Path("username") username: String):Call<List<FollowResponseItem>>
}