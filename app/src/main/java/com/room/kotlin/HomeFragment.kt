package com.room.kotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.room.kotlin.databinding.FragmentHomeBinding
import org.json.JSONArray

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var endemikAdapter: EndemikAdapter
    private val endemikList = ArrayList<Endemik>()
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setRV() {
        endemikAdapter = EndemikAdapter(requireActivity().application, endemikList)
        val layoutManager = GridLayoutManager(requireActivity().application, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = endemikAdapter

        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRV()
    }

    private fun getData() {
        val queue = Volley.newRequestQueue(requireActivity().application)
        val url = "https://hendroprwk08.github.io/data_endemik/endemik.json"

        Log.d("TAG", "getData: "+ url)

        // request json response dari URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val responseArray = JSONArray(response)

                var id: String
                var nama: String
                var deskripsi: String
                var foto: String

//                for (i in 0 until responseArray.length()) {
                for (i in 0 until 20) { // 20 saja
                    val jsonObject = responseArray.getJSONObject(i)
                    id = jsonObject.optString("id")
                    nama = jsonObject.optString("nama")
                    deskripsi = jsonObject.optString("deskripsi")
                    foto = jsonObject.optString("foto")

                    endemikList.add(Endemik(id, nama, deskripsi, foto))
                }

                endemikAdapter.notifyDataSetChanged()

                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            },
            {
                Toast.makeText(requireActivity().application, "Koneksi: Gagal terhubung", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}