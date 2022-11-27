package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.holder.AppointmentHolder
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Schedule

class AppointmentAdapter(private val context: Context,
                         private val schedule: List<Schedule>
): RecyclerView.Adapter<AppointmentHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val layoutInflater = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_appointment,
                parent,
                false
            )
        println("ffffffffffffffffffff" + schedule.toString())
        return AppointmentHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        val item = schedule[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = schedule.size


}
