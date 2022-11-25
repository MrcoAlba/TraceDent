package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.OLDAPI.Clinic
import the.goats.tracedent.api.OLDAPI.Patient
import the.goats.tracedent.api.nuevoApi.NewApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentInfoDentistBinding
import the.goats.tracedent.databinding.FragmentInfoPatientBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class InfoPatientFragment : BaseFragment<FragmentInfoPatientBinding>(FragmentInfoPatientBinding::inflate) {

    private lateinit var activityParent : MainActivity
    private lateinit var patient : Patient
    private lateinit var idCita : String
    private lateinit var mService : RetrofitService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        mService = Common.retrofitService


        val bundle : Bundle = Bundle()
        bundle.putString("id_cita", requireArguments().getString("id_cita"))

        idCita = requireArguments().getString("id_cita").toString()

        binding.butRegresarPaciente.setOnClickListener      { activityParent.onBackPressed() }
        obtenerPaciente(idCita)
    }


    private fun obtenerPaciente(id_cita : String) {
        mService.getPatient(id_cita = id_cita).enqueue(object: Callback<NewApiResponse<Patient>> {
            override fun onResponse(
                call: Call<NewApiResponse<Patient>>,
                response: Response<NewApiResponse<Patient>>
            ) {
                fill(response.body()!!)
            }

            override fun onFailure(call: Call<NewApiResponse<Patient>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }

        })
    }



    private fun fill(response : NewApiResponse<Patient>) {

        var lista = response.data

        if(lista.size > 0) {
            lista.map {
                binding.txtNombrePaciente.text = it.person.first_name + " " + it.person.last_name
                binding.txtTelefonoPaciente.text = it.person.user.phone_number.toString()
                binding.txtDireccionPaciente.text = it.person.user.direction + "," + " " + it.person.user.district
                binding.txtGeneroPaciente.text = it.person.gender
                binding.txtDNIPaciente.text = it.person.dni.toString()
            }
        }
    }


}