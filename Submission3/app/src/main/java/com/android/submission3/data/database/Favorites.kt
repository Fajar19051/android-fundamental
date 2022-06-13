package com.android.submission3.data.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["login", "avatarUrl"], unique = true)])
@Parcelize
data class Favorites(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = ""
) : Parcelable