package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.adapter.AppointmentAdapter
import the.goats.tracedent.adapter.MyClinicAdapter
import the.goats.tracedent.adapter.MyDentistAdapter
import the.goats.tracedent.common.Common
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.databinding.FragmentViewAppointmentBinding
import the.goats.tracedent.model.*
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class ViewAppointmentFragment
    : BaseFragment<FragmentViewAppointmentBinding>(FragmentViewAppointmentBinding::inflate), SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent: MainActivity
    private lateinit var layoutManager: LinearLayoutManager
    private  var adapter: AppointmentAdapter? = null
    private var filtro: String = "total"
    private lateinit var id: String
    private lateinit var tipo: String
    override fun onStart() {
        super.onStart()
        adapter = null
    }


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        // Retrofit service
        mService        =   Common.retrofitService

        //Listeners
        //binding.svSearcher.setOnQueryTextListener(this)

        //recycler view
        mService                                = Common.retrofitService
        binding.rvListadodata.setHasFixedSize(true)
        layoutManager                           = LinearLayoutManager(requireContext())
        binding.rvListadodata.layoutManager     = layoutManager
        setUserTypeLogic()

        getTheList("")
        binding.btnPastAppointments.setOnClickListener {    filtro = "Fin"
                                                            getTheList("") }
        binding.btnLiveAppointments.setOnClickListener {    filtro = "Fin"
            getTheList("") }
        binding.btnTotalAppointments.setOnClickListener {    filtro = "total"
            getTheList("") }

    }
    private fun setUserTypeLogic() {
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        val userType = prefs.getString(getString(R.string.sp_user_type),null)
        if (userType == "patient"){
            tipo = "patient"
            val preferences = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences), 0)
            id = preferences.getString(getString(R.string.sp_patient_id), " ").toString()
            return
        }

        if (userType == "dentist"){
            tipo = "dentist"
            val preferences = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences), 0)
            id = preferences.getString(getString(R.string.sp_dentist_id), " ").toString()
            return
        }
        if (userType == "clinic"){
            tipo = "clinic"
            val preferences = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences), 0)
            id = preferences.getString(getString(R.string.sp_clinic_id), " ").toString()
            return
        }

    }
    private fun getTheList(query: String) {
        Log.w("getTheList","inicio")
        if (filtro=="total") {
            getAppointments(query, 10)
        }
        else if (filtro=="Fin"){
            getAppointments(query, 7)
        }else if(filtro=="Activa"){
            getAppointments(query, 5)
        }
        Log.w("getTheList","fin")
    }
    private fun getAppointments(name: String, status: Int) {
        Log.w("getAllDentistList","inicio")
        if(tipo == "patient"){
            mService
                .getAllScheduleByPatientId(id, "", "", status, "")
                .enqueue(object: Callback<ApiResponse<Schedule>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Schedule>>,
                        response: Response<ApiResponse<Schedule>>
                    ) {
                        try {
                            adapter = AppointmentAdapter(requireContext(), response.body()!!.data)
                            adapter!!.notifyDataSetChanged()
                            binding.rvListadodata.adapter = adapter
                        }catch (e:Exception) {
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse<Schedule>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
        else if(tipo == "dentist"){
            mService
                .getAllScheduleByDentistId(id, "", "", status, "")
                .enqueue(object: Callback<ApiResponse<Schedule>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Schedule>>,
                        response: Response<ApiResponse<Schedule>>
                    ) {
                        try {
                            adapter = AppointmentAdapter(requireContext(), response.body()!!.data)
                            adapter!!.notifyDataSetChanged()
                            binding.rvListadodata.adapter = adapter
                        }catch (e:Exception) {
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse<Schedule>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
        else if(tipo == "clinic"){
            mService
                .getAllScheduleByClinicId(id, "", "", status)
                .enqueue(object: Callback<ApiResponse<Schedule>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Schedule>>,
                        response: Response<ApiResponse<Schedule>>
                    ) {
                        try {
                            adapter = AppointmentAdapter(requireContext(), response.body()!!.data)
                            adapter!!.notifyDataSetChanged()
                            binding.rvListadodata.adapter = adapter
                        }catch (e:Exception) {
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse<Schedule>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.w("onQueryTextSubmit","inicio")
        getTheList(query?:"")
        //binding.svSearcher.clearFocus()
        Log.w("onQueryTextSubmit","fin")
        return true
    }
    override fun onQueryTextChange(query: String?): Boolean {
        Log.w("onQueryTextChange","inicio")
        getTheList(query?:"")

        Log.w("onQueryTextChange","fin")
        return true
    }
    override fun onClose(): Boolean {
        Log.w("onClose","inicio")
        getTheList("")
        //binding.svSearcher.clearFocus()
        Log.w("onClose","fin")
        return true
    }
}











































