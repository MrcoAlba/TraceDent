package the.goats.tracedent.views.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.views.model.Dentista
import res.layout.*

class MyDentistaAdapter(private val context:Context, private val dentistaList:MutableList<Dentista>): RecyclerView.Adapter<MyDentistaAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dentistaList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var tvNombre:TextView
        var tvdistrito:TextView
        var tvrating:TextView

        init {
            tvNombre=itemView.tvNombre
            tvdistrito
            tvrating
        }
    }
}