package the.goats.tracedent.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.databinding.ItemDentistBinding
import the.goats.tracedent.model.Dentist

class MyDentistHolder (
    view: View
) : RecyclerView.ViewHolder(view){
    val binding = ItemDentistBinding.bind(view)

    fun bind(dentist: Dentist){
        binding.tvTipo.text="Dentista"
        binding.tvDistrito.text = dentist.person!!.user!!.district
        binding.tvNombre.text = dentist.person!!.first_name + " " + dentist.person!!.last_name
        binding.tvRating.text = dentist.rating.toString()
    }
}