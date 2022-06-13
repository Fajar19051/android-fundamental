package com.android.submission3.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1)
abstract class FavoritesRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoritesRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoritesRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoritesRoomDatabase::class.java, "note_database"
                    ).build()
                }
            }
            return INSTANCE as FavoritesRoomDatabase
        }
    }
}