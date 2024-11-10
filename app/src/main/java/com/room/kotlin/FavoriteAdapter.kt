package com.room.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.room.kotlin.databinding.RowItemLayoutBinding

class FavoriteAdapter (var context: Context, private var favoriteList: List<Favorite>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>(){

    //binding layout: 1. ganti "binding: ItemRowLayoutBinding" dan "binding.root"
    class MyViewHolder(val binding: RowItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.MyViewHolder {
        //binding layout: 2. tarik layout
        val binding = RowItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        //binding layout: 3. letakkan nilai pada layout
        holder.binding.tvNama.text = favoriteList[position].nama

        Glide.with(holder.itemView.context)
            .load(favoriteList[position].foto)
            .placeholder(R.drawable.baseline_broken_image_24)
            .into(holder.binding.gambar)

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("_id", favoriteList[position].endemikId)
            bundle.putString("_nama", favoriteList[position].nama)
            bundle.putString("_deskripsi", favoriteList[position].deskripsi)
            bundle.putString("_foto", favoriteList[position].foto)

            val i = Intent(context, DetilActivity::class.java)
            i.putExtras(bundle)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}

