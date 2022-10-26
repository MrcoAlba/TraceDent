package the.goats.tracedent.views.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import the.goats.tracedent.R
import the.goats.tracedent.api.UserLoginResponse
import the.goats.tracedent.databinding.FragmentRegisterG0Binding
import the.goats.tracedent.databinding.FragmentUsuarioBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class UsuarioFragment
    : BaseFragment<FragmentUsuarioBinding>(FragmentUsuarioBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private lateinit var user : UserLoginResponse




    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "UsuarioFragment", "onViewCreated")
        //Getting user
        val prefs : SharedPreferences = activityParent.getSharedPreferences("app.TraceDent",0)
        val userJson = prefs.getString("usuario","")
        user = Gson().fromJson(userJson,UserLoginResponse::class.java)
        val suscripcion = user.cuenta[0].suscripcion
        //Listeners
        binding.btnSuscribirse.setOnClickListener                     { GetInfo(suscripcion) }

        if (user.cuenta[0].tipoUsuario != 2){
            binding.btnSuscribirse.visibility = View.GONE
        }
    }

    //Selected option
    private fun GetInfo(option:Boolean){
        //Save in memory that client card view was pressed
        val bundle = Bundle()
        bundle.putBoolean("suscripcion", option)
        communicator
            .goToAnotherFragment(
                bundle,
                Suscripcion01Fragment(),
                activityParent.containerView,
                "UsuarioSuscripcion01Fragment"
            )
    }
}
