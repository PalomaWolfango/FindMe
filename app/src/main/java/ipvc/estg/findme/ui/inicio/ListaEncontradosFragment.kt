package ipvc.estg.findme.ui.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.findme.R
import ipvc.estg.findme.dataclasses.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_listar.view.*


class ListaEncontradosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_listar, container, false)

        // recycler view
        val recyclerView: RecyclerView = view.recyclerCasos
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.gato, "Sphynx ", ""+i+"/"+i+"/2021", "Viana do Castelo"))
        }
        val adapter = CasoAdapter(data)
        recyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return view
    }

}