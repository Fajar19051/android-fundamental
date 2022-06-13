package com.android.submission3.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String = "",
    var avatarUrl: String = "",
    var favorited: Boolean = false
) : Parcelable