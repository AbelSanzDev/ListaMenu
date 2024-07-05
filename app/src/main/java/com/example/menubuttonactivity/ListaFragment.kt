package com.example.menubuttonactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var originalList: ArrayList<String>
    private lateinit var filteredList: ArrayList<String>
    private lateinit var arrayList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var searchView: SearchView
    private lateinit var txtLista : TextView
    private lateinit var imgSeachIcon : ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        listView = view.findViewById(R.id.lstAlumnos)
        searchView = view.findViewById(R.id.srcLista)
        txtLista = view.findViewById(R.id.txtLista)
        imgSeachIcon = view.findViewById(R.id.imgSeachIcon)

        val items = resources.getStringArray(R.array.alumnos)
        originalList = ArrayList(items.toList())
        filteredList = ArrayList(originalList)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val alumno: String = parent.getItemAtPosition(position).toString()
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Lista de Alumnos")
            builder.setMessage("$position: $alumno")
            builder.setPositiveButton("OK") { dialog, which -> }
            builder.show()
        }

        imgSeachIcon.setOnClickListener {
            txtLista.text = ""
            imgSeachIcon.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return false
            }

        })
        return view

    }
    private fun filterList(query: String?) {
        if (query.isNullOrEmpty()) {
            filteredList.clear()
            filteredList.addAll(originalList)
        } else {
            val queryWords = query.lowercase().split(" ")
            val newFilteredList = originalList.filter { item ->
                val itemLower = item.lowercase()
                queryWords.all { word -> itemLower.contains(word) }
            }
            filteredList.clear()
            filteredList.addAll(newFilteredList)
        }
        adapter.notifyDataSetChanged()
        listView.visibility = if (filteredList.isEmpty()) View.GONE else View.VISIBLE
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}