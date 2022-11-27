package the.goats.tracedent.adapter.holder

import android.view.View
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.ItemActualRecluitmentsBinding
import the.goats.tracedent.databinding.ItemAppointmentBinding
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Schedule
import the.goats.tracedent.model.Speciality

class AppointmentHolder (view:View): RecyclerView.ViewHolder(view) {
    val mService : RetrofitService = Common.retrofitService
    val binding = ItemAppointmentBinding.bind(view)
    var horas: List<String> = listOf(
        "12:00am", "12:30am",
        "01:00am", "01:30am",
        "02:00am", "02:30am",
        "03:00am", "03:30am",
        "04:00am", "04:30am",
        "05:00am", "05:30am",
        "06:00am", "06:30am",
        "07:00am", "07:30am",
        "08:00am", "08:30am",
        "09:00am", "09:30am",
        "10:00am", "10:30am",
        "11:00am", "11:30am",
        "12:00am", "12:30am",
        "01:00pm", "01:30pm",
        "02:00pm", "02:30pm",
        "03:00pm", "03:30pm",
        "04:00pm", "04:30pm",
        "05:00pm", "05:30pm",
        "06:00pm", "06:30pm",
        "07:00pm", "07:30pm",
        "08:00pm", "08:30pm",
        "09:00pm", "09:30pm",
        "10:00pm", "10:30pm",
        "11:00pm", "11:30pm",
        "12:00pm", "12:30pm"
    )

    fun bind(schedule: Schedule){
        getDentist(schedule)
    }

    private fun getDentist(schedule: Schedule){
        mService.getDentistById(schedule.id_dentist)
            .enqueue(object: Callback<ApiResponse<Dentist>> {
                override fun onResponse(
                    call: Call<ApiResponse<Dentist>>,
                    response: Response<ApiResponse<Dentist>>
                ) {
                    val dentist = response.body()!!.data[0]
                    println("nnnnnnnnnnnnnnnnnnnnnnnnnnn"+dentist.toString())
                    println("oooooooooooooooooooooooooooo"+schedule.toString())
                    if (schedule.id_speciality == null){
                        binding.tvDentista.text = dentist.person!!.first_name + " " + dentist.person!!.last_name
                        binding.tvEspecialidad.text = "Genérico"
                        binding.tvFechaCita.text = schedule.date + " | " + horas[schedule.time!!]
                        if(schedule.sttus == 5){
                            binding.tvEstado.text = "Cita separada"
                        }else if(schedule.sttus == 7){
                            binding.tvEstado.text = "Cita finalizada"
                        }else{
                            binding.tvEstado.text = "La cita aun se encuentra en proceso"
                        }

                    }else{
                        getSpeciality(schedule, dentist)
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                    println(t.message)
                }

            })
    }

    private fun getSpeciality(schedule: Schedule, dentist: Dentist){
        println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
        mService.getSpeciality("0", "100","", schedule.id_speciality!!)
            .enqueue(object: Callback<ApiResponse<Speciality>> {
                override fun onResponse(
                    call: Call<ApiResponse<Speciality>>,
                    response: Response<ApiResponse<Speciality>>
                ) {
                    println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
                    val speciality = response.body()!!.data[0]
                    binding.tvDentista.text = dentist.person!!.first_name + " " + dentist.person!!.last_name
                    binding.tvEspecialidad.text = speciality.name
                    binding.tvFechaCita.text = schedule.date + " | " + horas[schedule.time!!]
                }

                override fun onFailure(call: Call<ApiResponse<Speciality>>, t: Throwable) {
                    println(t.message)
                }

            })
    }


}