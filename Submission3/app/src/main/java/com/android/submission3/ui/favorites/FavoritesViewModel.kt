package com.android.submission3.ui.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.submission3.data.database.Favorites
import com.android.submission3.data.repository.FavoriteRepository

class FavoritesViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorites>> = mFavoriteRepository.getAllFavorites()
}