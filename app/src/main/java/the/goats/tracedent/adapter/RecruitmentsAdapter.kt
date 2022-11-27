package the.goats.tracedent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.holder.RecruitmentsHolder
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Recruitment

class RecruitmentsAdapter(private val context: Context,
                          private val recruitments: List<Recruitment>,
                          val onCancelPressed:()->Unit
): RecyclerView.Adapter<RecruitmentsHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentsHolder {
        val layoutInflater = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_actual_recluitments,
                parent,
                false
            )
        return RecruitmentsHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: RecruitmentsHolder, position: Int) {
        val item = recruitments[position]

        holder.binding.btnAceptar.setOnClickListener { onCancelPressed() }

        getDentitst(item.id_dentist!!,holder)
    }

    override fun getItemCount(): Int = recruitments.size

    private fun getDentitst(clinicId: String, holder: RecruitmentsHolder){
        Common.retrofitService.getDentistById(id=clinicId)
            .enqueue(object: Callback<ApiResponse<Dentist>> {
                override fun onResponse(
                    call: Call<ApiResponse<Dentist>>,
                    response: Response<ApiResponse<Dentist>>
                ) {
                    val dentist = (response.body() as ApiResponse<Dentist>).data[0]
                    holder.binding.tvNombre.text = dentist.person!!.first_name + dentist.person!!.last_name
                    holder.binding.tvTextoInicio.text = ""
                }

                override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                    println(t.message)
                }

            })
    }
}
