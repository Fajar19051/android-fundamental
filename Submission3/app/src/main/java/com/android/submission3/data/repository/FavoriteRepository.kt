package com.android.submission3.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.submission3.data.database.*

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoritesDao

    init {
        val db = FavoritesRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorites>> = mFavoritesDao.getAllFavorites()

    fun getFavoriteByLogin(login: String): LiveData<Favorites> =
        mFavoritesDao.getFavoriteByLogin(login)

    suspend fun insert(favorites: Favorites) {
        mFavoritesDao.insert(favorites)
    }

    suspend fun delete(login: String) {
        mFavoritesDao.delete(login)
    }
}