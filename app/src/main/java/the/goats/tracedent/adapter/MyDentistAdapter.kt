package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.R
import the.goats.tracedent.adapter.holder.MyDentistHolder
import the.goats.tracedent.model.Dentist

class MyDentistAdapter (
    private val context : Context,
    private val dentistList: List<Dentist>,
    val dentistSelected : (Dentist)->Unit
) : RecyclerView.Adapter<MyDentistHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDentistHolder {
        val layoutInflater = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_dentist,
                parent,
                false
            )
        return MyDentistHolder(layoutInflater
        )
    }

    override fun onBindViewHolder(holder: MyDentistHolder, position: Int) {
        val item = dentistList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            dentistSelected(item)
        }
    }

    override fun getItemCount(): Int = dentistList.size


}