package com.example.menubuttonactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView

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
    private lateinit var lstAlumnos:ListView;
    private lateinit var arrayList:ArrayList<String>;
    private lateinit var adapter: ArrayAdapter<String>;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        lstAlumnos = view.findViewById(R.id.lstAlumnos);
        val items = resources.getStringArray(R.array.alumnos);

        arrayList=java.util.ArrayList();
        arrayList.addAll(items);

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,arrayList);

        lstAlumnos.adapter=adapter;
        return view;
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