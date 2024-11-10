package com.room.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.room.kotlin.databinding.RowItemLayoutBinding

class EndemikAdapter(var context: Context, private var endemikList: List<Endemik>) :
    RecyclerView.Adapter<EndemikAdapter.MyViewHolder>() {

    //binding layout: 1. ganti "binding: ItemRowLayoutBinding" dan "binding.root"
    class MyViewHolder(val _binding: RowItemLayoutBinding) : RecyclerView.ViewHolder(_binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndemikAdapter.MyViewHolder {
        //binding layout: 2. tarik layout
        val _binding = RowItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: EndemikAdapter.MyViewHolder, position: Int) {
        //binding layout: 3. letakkan nilai pada layout
        holder._binding.tvNama.text = endemikList[position].getNama()

        Glide.with(holder.itemView.context)
            .load(endemikList[position].getFoto())
            .placeholder(R.drawable.baseline_broken_image_24)
            .into(holder._binding.gambar)

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("_id", endemikList[position].getId())
            bundle.putString("_nama", endemikList[position].getNama())
            bundle.putString("_deskripsi", endemikList[position].getDeskripsi())
            bundle.putString("_foto", endemikList[position].getFoto())

            val i = Intent(context, DetilActivity::class.java)
            i.putExtras(bundle)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return endemikList.size
    }

}