package com.android.submission3.data.network.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("followers")
    val followers: Int,
    @field:SerializedName("following")
    val following: Int,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("public_repos")
    val publicRepos: Int
)
