package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.model.Patient
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentInfoPatientBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Schedule
import the.goats.tracedent.model.Speciality
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class InfoPatientFragment(val schedule: Schedule, val dentist: Dentist, val speciality: Speciality?) : BaseFragment<FragmentInfoPatientBinding>(FragmentInfoPatientBinding::inflate) {

    private lateinit var activityParent : MainActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        // Retrofit
        mService = Common.retrofitService

        val bundle : Bundle = Bundle()
        bundle.putString("id_cita", requireArguments().getString("id_cita"))

        binding.butRegresarPaciente.setOnClickListener      { activityParent.onBackPressed() }
        obtenerPaciente()
    }


    private fun obtenerPaciente() {
        mService.getPatientById(schedule.id_patient).enqueue(object: Callback<ApiResponse<Patient>> {
            override fun onResponse(
                call: Call<ApiResponse<Patient>>,
                response: Response<ApiResponse<Patient>>
            ) {
                fill(response.body()!!.data[0])
            }

            override fun onFailure(call: Call<ApiResponse<Patient>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }

        })
    }

    private fun fill(patient : Patient) {
                binding.txtNombrePaciente.text = patient.person?.first_name + " " + patient.person?.last_name
                binding.txtTelefonoPaciente.text = patient.person?.user?.phone_number.toString()
                binding.txtDireccionPaciente.text = patient.person?.user?.direction + "," + " " + patient.person?.user?.district
                binding.txtGeneroPaciente.text = patient.person?.gender
                binding.txtDNIPaciente.text = patient.person?.dni.toString()

        }
    }