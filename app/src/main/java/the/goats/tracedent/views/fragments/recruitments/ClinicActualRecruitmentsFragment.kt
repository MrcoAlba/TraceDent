package the.goats.tracedent.views.fragments.recruitments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.RecruitmentsAdapter
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentTitleReciclerviewBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.model.Recruitment
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class ClinicActualRecruitmentsFragment: BaseFragment<FragmentTitleReciclerviewBinding>
    (FragmentTitleReciclerviewBinding::inflate){

    private lateinit var activityParent : MainActivity
    private var clinic_id: String? = null
    private var adapter: RecruitmentsAdapter? = null
    private var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        logout          =   requireActivity() as Credential.LogOut

        binding.txtTitulo.text = "Reclutamientos Activos"
        binding.reciclerView.setHasFixedSize(true)

        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)

        clinic_id = prefs.getString(getString(R.string.sp_clinic_id),null)

        getAllRecruitments()
    }

    private fun getAllRecruitments(){
        Common.retrofitService.getAllRecruitments(offset="0",limit="100", id_clinic = clinic_id!!,id_dentist = "")
            .enqueue(object: Callback<ApiResponse<Recruitment>> {
                override fun onResponse(
                    call: Call<ApiResponse<Recruitment>>,
                    response: Response<ApiResponse<Recruitment>>
                ) {
                    try {
                        var recruitmentsResponse = response.body() as ApiResponse<Recruitment>
                        var recruitments = recruitmentsResponse.data.filter { it.sttus == 2 }

                        if (recruitments.isEmpty()){
                            binding.reciclerView.visibility = View.GONE
                            binding.textView4.visibility = View.VISIBLE
                            return
                        }
                        binding.textView4.visibility = View.GONE
                        binding.reciclerView.visibility = View.VISIBLE

                        adapter = RecruitmentsAdapter(requireActivity(),
                            recruitments)
                        {
                            dialog = activityParent.createDialog("¿Realmente desea terminar este reclutamiento?",
                                "Cancelar",
                                "Aceptar",
                                {
                                    dialog!!.hide()
                                },{
                                    doOnConfirmAccept()
                                    dialog!!.hide()
                                })
                            dialog!!.show()
                        }
                        adapter!!.notifyDataSetChanged()
                        binding.reciclerView.adapter = adapter

                    }catch (ex: Exception){
                        println(ex)
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Recruitment>>, t: Throwable) {
                    println("GAAAAAAAERRORGAAAAAAAAA")
                }

            })
    }

    private fun doOnConfirmAccept(){
        //TODO
    }

}