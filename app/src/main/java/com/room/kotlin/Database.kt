package com.room.kotlin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}