package com.room.kotlin

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.room.kotlin.databinding.ActivityDetilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class DetilActivity : AppCompatActivity() {
    val TAG = "DetilActivity"
    private lateinit var binding: ActivityDetilBinding

    lateinit var endemikId: String
    lateinit var nama: String
    lateinit var deskripsi: String
    lateinit var foto: String

    var isDelete: Boolean = false
    var favoriteList: List<Favorite> = listOf() // List kosong


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //back button
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val i = intent
        endemikId = i.getStringExtra("_id").toString()
        nama = i.getStringExtra("_nama").toString()
        deskripsi = i.getStringExtra("_deskripsi").toString()
        foto = i.getStringExtra("_foto").toString()
    }

    override fun onStart() {
        super.onStart()

        binding.toolbar.title = nama
        binding.tvDeskripsi.text = deskripsi
        Glide.with(this@DetilActivity)
            .load(foto)
            .into(binding.image)

        binding.btnFavorite.setOnClickListener {
            if (isDelete) {
                GlobalScope.launch(Dispatchers.IO) {
                    MainActivity.db.favoriteDao().deleteByEndemikId(endemikId)
                }

                GlobalScope.launch(Dispatchers.Main) {
                    setButton(0) // Ubah ke favorite
                }
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    MainActivity.db.favoriteDao().insert(
                        Favorite(
                            endemikId,
                            nama,
                            deskripsi,
                            foto
                        )
                    )
                }

                GlobalScope.launch(Dispatchers.Main) {
                    setButton(1) // Ubah ke delete
                }
            }
        }

        cekFavorite()
    }

    private fun setButton(status: Int) {
        if (status == 1) { //Delete
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            binding.btnFavorite.backgroundTintList = colorStateList

            binding.btnFavorite.setIconResource(R.drawable.baseline_delete_24)

            binding.btnFavorite.setText(R.string.delete)

            isDelete = true
        } else {
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.accent))
            binding.btnFavorite.backgroundTintList = colorStateList

            binding.btnFavorite.setIconResource(R.drawable.baseline_favorite_24)

            binding.btnFavorite.setText(R.string.favorite)

            isDelete = false
        }
    }

    private fun cekFavorite() {
        GlobalScope.launch(Dispatchers.IO) {
            favoriteList = MainActivity.db.favoriteDao().selectByEndemikId(endemikId)
        }

        GlobalScope.launch(Dispatchers.Main) {
            if (favoriteList.size != 0) {
                setButton(1)
            } else {
                setButton(0)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}