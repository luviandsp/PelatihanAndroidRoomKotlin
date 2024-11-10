package com.room.kotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.room.kotlin.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var context: Context
    val TAG = "FavoriteFragment"
    var favoriteList: MutableList<Favorite> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setup()

        binding.btnDelete.setOnClickListener {
            GlobalScope.launch {
                MainActivity.db.favoriteDao().delete()
            }

            // kosongkan recyclerview adapter
            favoriteList.clear()

            favoriteAdapter.notifyDataSetChanged()
        }
    }

    private fun setup() {
        displayData()
        Thread.sleep(500L)
        setRV()
    }

    private fun setRV() {
        val layoutManager = GridLayoutManager(requireActivity().application, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = favoriteAdapter
    }

    // ambil data dari table
    private fun displayData() = GlobalScope.launch {
        favoriteList = MainActivity.db.favoriteDao().getAll().toMutableList()
        favoriteAdapter = FavoriteAdapter(requireActivity().application, favoriteList)
    }

    override fun onResume() {
        super.onResume()
        setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
