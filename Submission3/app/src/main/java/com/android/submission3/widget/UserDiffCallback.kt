package com.android.submission3.widget

import androidx.recyclerview.widget.DiffUtil
import com.android.submission3.data.models.User

class UserDiffCallback(
    private val mOldUserList: List<User>,
    private val mNewUserList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorites = mOldUserList[oldItemPosition]
        val newFavorites = mNewUserList[newItemPosition]
        return oldFavorites.login == newFavorites.login && oldFavorites.avatarUrl == newFavorites.avatarUrl
    }
}