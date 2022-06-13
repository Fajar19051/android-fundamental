package com.android.submission3.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorites: Favorites)

    @Query("DELETE FROM favorites WHERE login = :login")
    suspend fun delete(login: String)

    @Query("SELECT * from favorites ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<Favorites>>

    @Query("SELECT * FROM favorites WHERE login = :login LIMIT 1")
    fun getFavoriteByLogin(login: String): LiveData<Favorites>
}