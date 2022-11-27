package the.goats.tracedent.views.fragments.recruitments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.MyDentistAdapter
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.api.clinic.ClinicRecruitDentistIdResponse
import the.goats.tracedent.api.clinic.ClinicRecruitDentistIdSend
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentSearchForRecruitmentBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class SearchForRecruitmentFragment: BaseFragment<FragmentSearchForRecruitmentBinding>(FragmentSearchForRecruitmentBinding::inflate),  SearchView.OnQueryTextListener {

    private lateinit var activityParent: MainActivity
    private lateinit var layoutManager: LinearLayoutManager
    private var adapter: MyDentistAdapter? = null
    private var dialog: AlertDialog? = null
    private var clinicId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        layoutManager   =   LinearLayoutManager(requireContext())

        binding.svSearcher.setOnQueryTextListener(this)
        binding.rvListadodata.setHasFixedSize(true)
        binding.rvListadodata.layoutManager = layoutManager

        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)

        clinicId = prefs.getString(getString(R.string.sp_clinic_id),null)
    }


    private fun getTheList(query: String) {
            Common.retrofitService.getAllDentistsList("","",query,"","").enqueue(object : Callback<ApiResponse<Dentist>> {
                override fun onResponse(
                    call: Call<ApiResponse<Dentist>>,
                    response: Response<ApiResponse<Dentist>>
                ) {
                    try {
                        val responseData = response.body() as ApiResponse<Dentist>

                        adapter =
                            MyDentistAdapter(requireContext(), responseData.data) {
                                try {
                                    dialog = activityParent.createDialog("¿Desea enviar una solicitud a este dentista?",
                                        "Cancelar",
                                        "Aceptar",
                                        {
                                            dialog!!.hide()
                                        },
                                        {
                                            println(it.id_dentist)
                                            doOnConfirmSendInvite(it.id_dentist?:"")
                                            dialog!!.hide()
                                        }
                                    )

                                    dialog!!.show()
                                } catch (ex: Exception) {
                                    println(ex)
                                    Toast.makeText(
                                        activityParent.baseContext, "Error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        adapter!!.notifyDataSetChanged()
                        binding.rvListadodata.adapter = adapter
                    } catch (e:Exception) {
                        Toast.makeText(
                            activityParent.baseContext, e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                    Log.e("gaaa!", t.message.toString())
                }
            })
    }

    private fun doOnConfirmSendInvite(idDentist: String){

        Common.retrofitService.clinicRecruitDentist(clinicId, ClinicRecruitDentistIdSend(idDentist))
            .enqueue(object: Callback<ApiResponse<ClinicRecruitDentistIdResponse>>{
                override fun onResponse(
                    call: Call<ApiResponse<ClinicRecruitDentistIdResponse>>,
                    response: Response<ApiResponse<ClinicRecruitDentistIdResponse>>
                ) {
                    try {
                        val apiResponse = response.body() as ApiResponse<ClinicRecruitDentistIdResponse>

                        if (apiResponse.message != "OK"){
                            Toast.makeText(
                                activityParent.baseContext, "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                        Toast.makeText(
                            activityParent.baseContext, "Solicitud enviada correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }catch (e: Exception){
                        Toast.makeText(
                            activityParent.baseContext, e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<ClinicRecruitDentistIdResponse>>,
                    t: Throwable
                ) {
                    Log.e("gaaa!", t.message.toString())
                }

            })
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        getTheList(p0?:"")
        binding.svSearcher.clearFocus()
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        getTheList(p0?:"")
        return true
    }

}
