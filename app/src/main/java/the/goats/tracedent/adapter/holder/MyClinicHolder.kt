package the.goats.tracedent.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.databinding.ItemDentistBinding
import the.goats.tracedent.api.OLDAPI.Clinic

class MyClinicHolder (
    view: View
) : RecyclerView.ViewHolder(view){
    val binding = ItemDentistBinding.bind(view)

    fun bind(clinic: Clinic){
        binding.tvTipo.text="Clinica"
        binding.tvDistrito.text = clinic.user!!.district
        binding.tvNombre.text = clinic.company_name
        binding.tvRating.text = clinic.rating.toString()
    }
}