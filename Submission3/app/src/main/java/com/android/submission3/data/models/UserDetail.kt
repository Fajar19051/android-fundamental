package com.android.submission3.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    var login: String = "",
    var avatarUrl: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var publicRepos: Int? = 0,
    var followers: Int? = 0,
    var following: Int? = 0,
) : Parcelable