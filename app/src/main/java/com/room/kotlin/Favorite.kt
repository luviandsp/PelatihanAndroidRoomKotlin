package com.room.kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @ColumnInfo(name = "endemik_id")
    val endemikId: String,
    @ColumnInfo(name = "nama")
    val nama: String,
    @ColumnInfo(name = "deskripsi")
    val deskripsi: String,
    @ColumnInfo(name = "foto")
    val foto: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}