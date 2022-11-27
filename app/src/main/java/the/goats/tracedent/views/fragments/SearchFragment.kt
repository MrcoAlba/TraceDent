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
import the.goats.tracedent.adapter.MyClinicAdapter
import the.goats.tracedent.adapter.MyDentistAdapter
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentSearchBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Person
import the.goats.tracedent.model.User
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class SearchFragment
    : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent: MainActivity
    private lateinit var layoutManager: LinearLayoutManager
    private  var adapter: MyDentistAdapter? = null
    private  var adapter2: MyClinicAdapter? = null
    private var filtro: String = "Clinicas"
    private lateinit var txtNombre: TextView
    private lateinit var txtDireccion: TextView
    private lateinit var txtRating: TextView
    private lateinit var butMasInfo: Button

    override fun onStart() {
        super.onStart()
        adapter = null
        adapter2 = null
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
        binding.svSearcher.setOnQueryTextListener(this)

        //recycler view
        mService                                = Common.retrofitService
        binding.rvListadodata.setHasFixedSize(true)
        layoutManager                           = LinearLayoutManager(requireContext())
        binding.rvListadodata.layoutManager     = layoutManager
        binding.chipgroup.check(binding.chipDen.id)

        txtNombre                               = activityParent.findViewById(R.id.txtNombre)
        txtDireccion                            = activityParent.findViewById(R.id.txtDireccion)
        txtRating                               = activityParent.findViewById(R.id.txtRating)
        butMasInfo                              = activityParent.findViewById(R.id.butMasInfo)

        getTheList("")
        choiceChips()

    }

    private fun choiceChips(){
        Log.w("choiceChips","inicio")
        binding.chipgroup
            .setOnCheckedChangeListener {
                    group, checkedId ->
                    val chip:Chip?=group.findViewById(checkedId)
                    if(chip?.isChecked==true){
                        filtro= chip.text as String
                        Toast.makeText(context,
                            "Filtro $filtro",
                            Toast.LENGTH_SHORT).show()
                        if(filtro=="Dentistas"){
                            getTheList("")
                        }else{
                            getTheList("")
                        }
                    }else{
                        filtro="Dentistas"
                        getTheList("")
                        Toast.makeText(context,
                            "Filtro Dentistas",
                            Toast.LENGTH_SHORT).show()
                    }
            }
        Log.w("choiceChips","fin")
    }
    private fun getTheList(query: String) {
        Log.w("getTheList","inicio")
        if (filtro=="Dentistas") {
            getAllDentistList(query)
        }
        else {
            getAllClinicList(query)
        }
        Log.w("getTheList","fin")
    }
    private fun getAllDentistList(name: String) {
        Log.w("getAllDentistList","inicio")
        mService
            .getAllDentistsList(limit = "100", offset = "0", name = name, latitude = "", longitude = "")
            .enqueue(object: Callback<ApiResponse<Dentist>> {
            override fun onResponse(
                call: Call<ApiResponse<Dentist>>,
                response: Response<ApiResponse<Dentist>>
            ) {
                try {
                    adapter = MyDentistAdapter(requireContext(), response.body()!!.data) {
                        try {
                            getOnClickDentist(it)
                        } catch (ex: Exception) {
                            Toast.makeText(
                                activityParent.baseContext, "No se encontro su busqueda",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    adapter!!.notifyDataSetChanged()
                    binding.rvListadodata.adapter = adapter
                }catch (e:Exception) {
                }
            }
            override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
        Log.w("getAllDentistList","fin")
    }
    private fun getAllClinicList(name: String) {
        Log.w("getAllClinicList","inicio")
        mService
            .getAllClinicsList(limit = "100", offset = "", name = name, latitude = "", longitude = "")
            .enqueue(object : Callback<ApiResponse<Clinic>> {
            override fun onResponse(
                call: Call<ApiResponse<Clinic>>,
                response: Response<ApiResponse<Clinic>>
            ) {
                try {
                    adapter2 = MyClinicAdapter(requireContext(), response.body()!!.data) {
                        try {
                            getOnClickClinic(it)
                        } catch (ex: Exception) {
                            Toast.makeText(
                                activityParent.baseContext, "No se encontro su busqueda",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    adapter2!!.notifyDataSetChanged()
                    binding.rvListadodata.adapter = adapter2
                } catch(e: Exception) {
                }
            }
            override fun onFailure(call: Call<ApiResponse<Clinic>>, t: Throwable) {
            }
        })
        Log.w("getAllClinicList","fin")
    }

    private fun getOnClickDentist(dentist: Dentist){
        Log.w("getOnClickDentist","inicio")
        try {
            val info: Dentist = dentist
            val infoPerson: Person? = dentist.person
            val infoUser: User? = dentist.person?.user
            txtNombre.text = infoPerson!!.first_name + " " + infoPerson!!.last_name
            txtDireccion.text = infoUser!!.direction
            txtRating.text = info.rating.toString()
            /*butMasInfo.setOnClickListener{*/
            val bundle: Bundle = Bundle()
            bundle.putString("id", info.id_dentist)
            bundle.putString("first_name", infoPerson.first_name)
            bundle.putString("last_name", infoPerson.last_name)
            bundle.putString("direction", txtDireccion.text.toString())
            bundle.putString("rating", txtRating.text.toString())
            bundle.putString("gender", infoPerson.gender)
            bundle.putString("district", infoUser.direction)
            bundle.putString("dni", infoPerson.dni.toString())
            bundle.putString("phone_number", infoUser.phone_number.toString())
            bundle.putString("ruc", info.ruc)

            communicator.goToAnotherFragment(
                bundle,
                InfoDentistFragment(),
                activityParent.containerView,
                "MapFragment2InfoDentistFragment"
            )
        } catch(e:Exception) {

        }
        /*}
        if(bottomSheetFragment.visibility == View.VISIBLE) {
            bottomSheetFragment.visibility = View.GONE
        }
        else{
            bottomSheetFragment.visibility = View.VISIBLE
        }*/
        Log.w("getOnClickDentist","fin")
    }
    private fun getOnClickClinic(it: Clinic){
        Log.w("getOnClickClinic","inicio")
        try {
            val info: Clinic = it
            val infoUser: User? = it.user
            txtNombre.text = info.company_name
            txtDireccion.text = infoUser!!.direction
            txtRating.text = info.rating.toString()
            /*butMasInfo.setOnClickListener{*/
            val bundle: Bundle = Bundle()
            bundle.putString("id", info.id_clinic)
            bundle.putString("company_name", info.company_name)
            bundle.putString("direction", txtDireccion.text.toString())
            bundle.putString("rating", txtRating.text.toString())
            bundle.putString("phone_number", infoUser.phone_number.toString())
            bundle.putString("district", infoUser.district.toString())
            bundle.putString("ruc", info.ruc)

            communicator.goToAnotherFragment(
                bundle,
                InfoClinicFragment(),
                activityParent.containerView,
                "MapFragment2InfoDentistFragment"
            )
            /*}
        if(bottomSheetFragment.visibility == View.VISIBLE) {
            bottomSheetFragment.visibility = View.GONE
        }
        else{
            bottomSheetFragment.visibility = View.VISIBLE
        }*/
        }catch(e: Exception) {
        }
        Log.w("getOnClickClinic","fin")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.w("onQueryTextSubmit","inicio")
        getTheList(query?:"")
        binding.svSearcher.clearFocus()
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
        binding.svSearcher.clearFocus()
        Log.w("onClose","fin")
        return true
    }
}











































