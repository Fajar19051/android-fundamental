package com.android.submission3.widget
import androidx.recyclerview.widget.DiffUtil
import com.android.submission3.data.database.Favorites

class FavoritesDiffCallback(
    private val mOldFavoritesList: List<Favorites>,
    private val mNewFavoritesList: List<Favorites>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoritesList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoritesList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoritesList[oldItemPosition].id == mNewFavoritesList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorites = mOldFavoritesList[oldItemPosition]
        val newFavorites = mNewFavoritesList[newItemPosition]
        return oldFavorites.login == newFavorites.login && oldFavorites.avatarUrl == newFavorites.avatarUrl
    }
}