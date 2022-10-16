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
        binding.tvDistrito.text = dentist.distrito
        binding.tvNombre.text = dentist.nombres
        binding.tvRating.text = dentist.rating
    }
}