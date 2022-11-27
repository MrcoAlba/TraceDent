package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentUsuarioBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.suscripcion.Suscripcion01Fragment

class UsuarioFragment
    : BaseFragment<FragmentUsuarioBinding>(FragmentUsuarioBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        logout          =   requireActivity() as Credential.LogOut
        // There are some rules to show all the content
        setUserTypeLogic()
        //Listeners
        binding.btnSuscribirse.setOnClickListener                     { continueToSubscriptionFragment()    }
        binding.btnCerrarSesion.setOnClickListener                    { signOut()                           }
    }

    private fun setUserTypeLogic() {
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)

        val userType = prefs.getString(getString(R.string.sp_user_type),null)

        if (userType == "patient"){
            binding.btnSuscribirse.visibility = View.GONE
        }

    }
    //Selected option
    private fun continueToSubscriptionFragment(){
        communicator
            .goToAnotherFragment(
                null,
                Suscripcion01Fragment(),
                activityParent.containerView,
                "Suscripcion01Fragment"
            )
    }
    // Sign out and clean the Share Preferences
    private fun signOut(){
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        if (prefs.edit().clear().commit()){
            logout.Main2Login()
        }
    }

}
