package ipvc.estg.findme.ui.inicio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.findme.R
import ipvc.estg.findme.dataclasses.ItemsViewModel
import ipvc.estg.findme.MenuTesteActivity

class CasoAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CasoAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.caso_card_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the data to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.image)
        holder.raca.text = ItemsViewModel.raca
        holder.data.text = ItemsViewModel.data
        holder.localidade.text = ItemsViewModel.localidade

        holder.itemView.setOnClickListener {
            // abrir activity de ver detalhes do caso
            //val intent = Intent(it.context, OpenCasoActivity::class.java)
            // To pass any data to next activity
            //intent.putExtra("casoId", CasoId.toString())
            // Start your next activity
            //it.context.startActivity(intent)
            Toast.makeText(it.context, "Clicked!", Toast.LENGTH_SHORT).show()
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val raca: TextView = itemView.findViewById(R.id.tvraca)
        val data: TextView = itemView.findViewById(R.id.tvdata)
        val localidade: TextView = itemView.findViewById(R.id.tvlocalidade)
    }
}