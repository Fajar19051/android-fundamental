package com.android.submission1.data
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(@Query("q") q: String): Call<SearchResponse>
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(@Path("username") username: String?): Call<DetailsResponse>
    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<FollowerResponse>>
    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingResponse>>
}