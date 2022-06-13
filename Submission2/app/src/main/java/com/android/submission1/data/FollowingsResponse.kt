package com.android.submission1.data
import com.google.gson.annotations.SerializedName

data class FollowingResponse(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("type")
    val type: String
)
