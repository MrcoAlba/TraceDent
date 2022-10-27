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
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Person
import the.goats.tracedent.model.Usuario
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class SearchFragment
    : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent: MainActivity
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mService: RetrofitService
    private  var adapter: MyDentistAdapter? = null
    private  var adapter2: MyClinicAdapter? = null
    private var filtro: String = "Dentistas"
    private lateinit var bottomSheetFragment: View
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
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "SearchFragment", "onViewCreated")


        //Listeners
        binding.svSearcher.setOnQueryTextListener(this)



        //recycler view
        mService = Common.retrofitService
        binding.rvListadodata.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvListadodata.layoutManager = layoutManager
        binding.chipgroup.check(binding.chipDen.id)
        txtNombre = activityParent.findViewById(R.id.txtNombre)
        txtDireccion = activityParent.findViewById(R.id.txtDireccion)
        txtRating = activityParent.findViewById(R.id.txtRating)
        butMasInfo = activityParent.findViewById(R.id.butMasInfo)
        bottomSheetFragment = activityParent.findViewById(R.id.bottomsheet)


        getAllDentistList()
        choiceChips()

    }

    private fun choiceChips(){
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
                            getAllDentistList()
                        }else{
                            getAllClinicList()
                        }
                    }else{
                        filtro="Dentistas"
                        getAllDentistList()
                        Toast.makeText(context,
                            "Filtro Dentistas",
                            Toast.LENGTH_SHORT).show()
                    }
            }
    }
    private fun getAllDentistList() {
        mService.getAllDentistsList().enqueue(object: Callback<MutableList<Dentist>> {
            override fun onResponse(
                call: Call<MutableList<Dentist>>,
                response: Response<MutableList<Dentist>>
            ) {
                try {
                    adapter = MyDentistAdapter(requireContext(), response.body() as List<Dentist>) {
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
            override fun onFailure(call: Call<MutableList<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getAllClinicList() {
        mService.getAllClinicsList().enqueue(object : Callback<MutableList<Clinic>> {
            override fun onResponse(
                call: Call<MutableList<Clinic>>,
                response: Response<MutableList<Clinic>>
            ) {
                try {
                    adapter2 = MyClinicAdapter(requireContext(), response.body() as List<Clinic>) {
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

            override fun onFailure(call: Call<MutableList<Clinic>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getTheList(query: String) {
        if (filtro=="Dentistas") {
            mService.getDentistsList(query).enqueue(object : Callback<MutableList<Dentist>> {
                override fun onResponse(
                    call: Call<MutableList<Dentist>>,
                    response: Response<MutableList<Dentist>>
                ) {
                    try {
                        adapter =
                            MyDentistAdapter(activityParent, response.body() as List<Dentist>) {
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
                    } catch(e: Exception) {

                    }
                    }

                override fun onFailure(call: Call<MutableList<Dentist>>, t: Throwable) {
                    Log.e("gaaa!", t.message.toString())
                }
            })
        }
        else {
            mService.getClinicList(query).enqueue(object : Callback<MutableList<Clinic>> {
                override fun onResponse(
                    call: Call<MutableList<Clinic>>,
                    response: Response<MutableList<Clinic>>
                ) {
                    try {
                        adapter2 =
                            MyClinicAdapter(requireContext(), response.body() as List<Clinic>) {
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
                    } catch (e:Exception) {

                    }
                }

                override fun onFailure(call: Call<MutableList<Clinic>>, t: Throwable) {
                    Log.e("gaaa!", t.message.toString())
                }
            })
        }
    }
    private fun getOnClickDentist(it:Dentist){
        try {
            val info: Dentist = it
            val infoPerson: Person? = it.person
            val infoUser: Usuario? = it.person?.user
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
    }
    private fun getOnClickClinic(it:Clinic){
        try {
            val info: Clinic = it
            val infoUser: Usuario? = it.user
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
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        getTheList(query?:"")
        binding.svSearcher.clearFocus()
        return true
    }
    override fun onQueryTextChange(query: String?): Boolean {
        getTheList(query?:"")
        return true
    }
    override fun onClose(): Boolean {
        if(filtro=="Dentistas"){
            getAllDentistList()
        }else{
            getAllClinicList()
        }
        binding.svSearcher.clearFocus()
        return true
    }
}