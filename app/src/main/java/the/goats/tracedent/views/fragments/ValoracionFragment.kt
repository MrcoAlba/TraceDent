package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentValoracionBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import kotlin.properties.Delegates

class ValoracionFragment
    : BaseFragment<FragmentValoracionBinding>(FragmentValoracionBinding::inflate){
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private var rating : Float = 0.0f
    private lateinit var id : String
    private lateinit var tipo : String

    //Fragment Lifecycle
    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        logout          =   requireActivity() as Credential.LogOut

        //Firebase Analytics
        mService = Common.retrofitService

        //Getting user
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)

        tipo = prefs.getString(getString(R.string.sp_user_type),"user").toString()

        id = requireArguments().getString("id").toString()

        //Listeners
        binding.butConfirmarValoracion.setOnClickListener              { subirValoracion(id, tipo) }
        binding.butCancelarValoracion.setOnClickListener               { activityParent.onBackPressed() }

    }


    private fun subirValoracion (id : String, tipo : String) {

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            rating = ratingBar.rating

        }
        if(tipo == "dentist") {
            /*mService.updateDentistById(id, DentistUpdateData(rating)).enqueue(object:
                Callback<SusResponse> {
                override fun onResponse(call: Call<SusResponse>, response: Response<SusResponse>) {
                    Log.i("","Se cambio el rating del dentista")
                }

                override fun onFailure(call: Call<SusResponse>, t: Throwable) {
                    Log.i("=============",t.message.toString())
                }
            })*/
        }
        else if (tipo == "clinic") {
           /*mService.updateClinicById(id, ClinicUpdateData(rating)).enqueue(object:
                Callback<SusResponse> {
                override fun onResponse(call: Call<SusResponse>, response: Response<SusResponse>) {
                    Log.i("","Se cambio el rating de la clinica")
                }

                override fun onFailure(call: Call<SusResponse>, t: Throwable) {
                    Log.i("=============",t.message.toString())
                }
            })*/
        }
    }
}