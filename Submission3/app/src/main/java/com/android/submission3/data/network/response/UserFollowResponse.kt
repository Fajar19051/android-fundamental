package com.android.submission3.data.network.response

import com.google.gson.annotations.SerializedName

data class UserFollowResponse(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("login")
    val login: String,
)
