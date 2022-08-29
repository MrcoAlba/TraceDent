package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.R
import the.goats.tracedent.room.DataDentista

class DataAdapter    (private val context : Context,
                      private val DataList: List<DataDentista>,
                      private val onClickListener: (DataDentista) -> Unit
):
RecyclerView.Adapter<DataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataViewHolder(context,layoutInflater.inflate(R.layout.item_data, parent, false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.render(
            DataList[position],
            onClickListener
        )
    }

    override fun getItemCount(): Int = DataList.size
}