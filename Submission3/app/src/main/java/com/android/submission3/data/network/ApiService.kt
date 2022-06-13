package com.android.submission3.data.network

import com.android.submission3.data.network.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(@Query("q") q: String): Call<SearchUserResponse>
    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserFollowResponse>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserFollowResponse>>
}