package com.room.kotlin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll():List<Favorite>

    @Query("SELECT * FROM favorite WHERE endemik_id = :id")
    fun selectByEndemikId(id: String):List<Favorite>

    @Insert
    fun insert(vararg favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun delete()

    @Query("DELETE FROM favorite WHERE endemik_id = :endemikId")
    fun deleteByEndemikId(endemikId: String)
}