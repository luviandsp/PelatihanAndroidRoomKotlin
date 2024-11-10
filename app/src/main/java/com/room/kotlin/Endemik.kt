package com.room.kotlin

class Endemik (id: String?, nama: String?, deskripsi: String?, foto: String?) {

    private var id: String
    private var nama: String
    private var deskripsi: String
    private var foto: String

    init {
        this.id = id!!
        this.nama = nama!!
        this.deskripsi = deskripsi!!
        this.foto = foto!!
    }

    fun getId(): String? {
        return id
    }

    fun getNama(): String? {
        return nama
    }

    fun getDeskripsi(): String? {
        return deskripsi
    }

    fun getFoto(): String? {
        return foto
    }

}