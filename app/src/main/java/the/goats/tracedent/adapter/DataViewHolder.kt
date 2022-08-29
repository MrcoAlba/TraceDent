package the.goats.tracedent.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.databinding.ItemDataBinding
import the.goats.tracedent.room.DataDentista

class DataViewHolder(private val context : Context, view : View) : RecyclerView.ViewHolder(view) {
    val binding = ItemDataBinding.bind(view)

    fun render(resultadoModel: DataDentista, onClickListener: (DataDentista) -> Unit){
        binding.tvnombre.text = resultadoModel.nombre
        binding.tvdistrito.text = resultadoModel.distrito
        binding.tvrating.text = resultadoModel.rating

        itemView.setOnClickListener{
            onClickListener(resultadoModel)
        }
    }
}