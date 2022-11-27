package the.goats.tracedent.views.fragments.recruitments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.InviteAdapter
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentTitleReciclerviewBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.model.Recruitment
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import java.util.prefs.Preferences

class DentistReceivedInvitationsFragment :BaseFragment<FragmentTitleReciclerviewBinding>(FragmentTitleReciclerviewBinding::inflate) {

    private lateinit var activityParent : MainActivity
    private var dentist_id: String? = null
    private var adapter: InviteAdapter? = null
    private var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        logout          =   requireActivity() as Credential.LogOut

        binding.txtTitulo.text = "Mis Invitaciones"
        binding.reciclerView.setHasFixedSize(true)
        binding.reciclerView.layoutManager = LinearLayoutManager(requireContext())

        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)

        dentist_id = prefs.getString(getString(R.string.sp_user_type),null)

        getAllRecruitments()
    }

    private fun getAllRecruitments(){
        Common.retrofitService.getAllRecruitments(offset="0",limit="100", id_clinic = "",id_dentist = dentist_id!!)
            .enqueue(object: Callback<ApiResponse<Recruitment>>{
                override fun onResponse(
                    call: Call<ApiResponse<Recruitment>>,
                    response: Response<ApiResponse<Recruitment>>
                ) {
                    try {
                        var recruitmentsResponse = response.body() as ApiResponse<Recruitment>
                        var recruitments = recruitmentsResponse.data.filter { it.sttus == 0 }

                        if (recruitments.isEmpty()){
                            binding.reciclerView.visibility = View.GONE
                            binding.textView4.visibility = View.VISIBLE
                            return
                        }
                        binding.textView4.visibility = View.GONE
                        binding.reciclerView.visibility = View.VISIBLE
                        adapter = InviteAdapter(requireActivity(),
                            recruitments,
                            {
                                dialog = activityParent.createDialog("¿Realmente desea rechazar esta solicitud?",
                                "Cancelar",
                                "Rechazar",
                                    {
                                        dialog!!.hide()
                                    },{
                                        activityParent.changeRecruitmentState(it.id_recruitment!!,"4"){
                                            getAllRecruitments()
                                        }
                                        dialog!!.hide()
                                    })
                                dialog!!.show()
                            },{
                                dialog = activityParent.createDialog("¿Realmente desea aceptar esta solicitud?",
                                    "Cancelar",
                                    "Aceptar",
                                    {
                                        dialog!!.hide()
                                    },{
                                        activityParent.changeRecruitmentState(it.id_recruitment!!,"2"){
                                            getAllRecruitments()
                                        }
                                        dialog!!.hide()
                                    })
                                dialog!!.show()

                            })
                        adapter!!.notifyDataSetChanged()
                        binding.reciclerView.adapter = adapter

                    }catch (ex: Exception){
                        Toast.makeText(
                            activityParent.baseContext, ex.message!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Recruitment>>, t: Throwable) {
                    Toast.makeText(
                        activityParent.baseContext, t.message!!,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }
}