package com.android.submission1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String = "",
    var avatarUrl: String? = null,
) : Parcelable