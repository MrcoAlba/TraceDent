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
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class InfoPatientFragment : BaseFragment<FragmentInfoPatientBinding>(FragmentInfoPatientBinding::inflate) {

    private lateinit var activityParent : MainActivity
    private lateinit var patient : Patient
    private lateinit var idCita : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        // Retrofit
        mService = Common.retrofitService

        val bundle : Bundle = Bundle()
        bundle.putString("id_cita", requireArguments().getString("id_cita"))

        idCita = requireArguments().getString("id_cita").toString()

        binding.butRegresarPaciente.setOnClickListener      { activityParent.onBackPressed() }
        /*obtenerPaciente(idCita)*/
    }


    /*private fun obtenerPaciente(id_cita : String) {
        mService.getPatient(id_cita = id_cita).enqueue(object: Callback<ApiResponse<Patient>> {
            override fun onResponse(
                call: Call<ApiResponse<Patient>>,
                response: Response<ApiResponse<Patient>>
            ) {
                fill(response.body()!!)
            }

            override fun onFailure(call: Call<ApiResponse<Patient>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }

        })
    }*/

    private fun fill(response : ApiResponse<Patient>) {
        var lista = response.data

        if(lista.isNotEmpty()) {
            lista.map {
                binding.txtNombrePaciente.text = it.person?.first_name + " " + it.person?.last_name
                binding.txtTelefonoPaciente.text = it.person?.user?.phone_number.toString()
                binding.txtDireccionPaciente.text = it.person?.user?.direction + "," + " " + it.person?.user?.district
                binding.txtGeneroPaciente.text = it.person?.gender
                binding.txtDNIPaciente.text = it.person?.dni.toString()
            }
        }
    }

}