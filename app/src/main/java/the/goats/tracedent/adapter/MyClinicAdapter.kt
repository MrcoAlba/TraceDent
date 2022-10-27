package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.R
import the.goats.tracedent.adapter.holder.MyClinicHolder
import the.goats.tracedent.adapter.holder.MyDentistHolder
import the.goats.tracedent.api.Clinic

class MyClinicAdapter (
    private val context : Context,
    private val clinicList: List<Clinic>,
    val clinicSelected : (Clinic)->Unit
) : RecyclerView.Adapter<MyClinicHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClinicHolder {
        val layoutInflater = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_dentist,
                parent,
                false
            )
        return MyClinicHolder(layoutInflater
        )
    }

    override fun onBindViewHolder(holder: MyClinicHolder, position: Int) {
        val item = clinicList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            clinicSelected(item)
        }
    }

    override fun getItemCount(): Int = clinicList.size


}