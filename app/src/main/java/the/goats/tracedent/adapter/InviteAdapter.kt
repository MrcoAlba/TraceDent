package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import the.goats.tracedent.R
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.adapter.holder.InviteHolder
import the.goats.tracedent.common.Common
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Recruitment

class InviteAdapter(private val context: Context,
                    private val recluitments: List<Recruitment>,
                    val onRejectPressed: (Recruitment)->Unit,
                    val onAcceptPressed: (Recruitment)->Unit
):RecyclerView.Adapter<InviteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteHolder {
        val layoutInflater = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_received_invitation,
                parent,
                false
            )
        return InviteHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: InviteHolder, position: Int) {
        val item = recluitments[position]

        holder.binding.btnAceptar.setOnClickListener { onAcceptPressed(item) }
        holder.binding.btnRechazar.setOnClickListener { onRejectPressed(item) }

        getClinicById(item.id_clinic!!,holder)

    }

    override fun getItemCount(): Int = recluitments.size

    private fun getClinicById(clinicId: String, holder: InviteHolder){
        Common.retrofitService.getClinicById(id=clinicId)
            .enqueue(object: Callback<ApiResponse<Clinic>>{
                override fun onResponse(
                    call: Call<ApiResponse<Clinic>>,
                    response: Response<ApiResponse<Clinic>>
                ) {
                    val clinic = (response.body() as ApiResponse<Clinic>).data[0]
                    holder.binding.tvNombre.text = clinic.company_name
                }

                override fun onFailure(call: Call<ApiResponse<Clinic>>, t: Throwable) {}

            })
    }

}