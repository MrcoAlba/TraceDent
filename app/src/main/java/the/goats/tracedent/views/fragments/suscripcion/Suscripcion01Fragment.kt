package the.goats.tracedent.views.fragments.suscripcion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentSuscripcion01Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class Suscripcion01Fragment
    : BaseFragment<FragmentSuscripcion01Binding>(FragmentSuscripcion01Binding::inflate) {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent: MainActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator = requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        activityParent.back = false
        // There are some rules to show all the content
        enableSubscriptionButton()
        //Listeners
        binding.btnSuscribirse.setOnClickListener { continueSubscriptionLogic() }
        binding.tvCancelarSusripcion.setOnClickListener { cancelSubscription() }
    }

    private fun enableSubscriptionButton() {
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        val subscription = prefs.getBoolean(getString(R.string.sp_subscription),false)
        enableButton(!subscription)
        if (subscription) {
            binding.btnSuscribirse.text = "Usted ya esta suscrito"
            binding.tvCancelarSusripcion.visibility = View.VISIBLE
        } else {
            binding.btnSuscribirse.text = "Suscribirse"
            binding.tvCancelarSusripcion.visibility = View.GONE
        }
    }
    private fun continueSubscriptionLogic() {
        communicator
            .goToAnotherFragment(
                null,
                Suscripcion02Fragment(),
                activityParent.containerView,
                "Suscripcion01FragmentSuscripcion02Fragment"
            )
    }
    private fun enableButton(b: Boolean) {
        binding.btnSuscribirse.isClickable = b
        binding.btnSuscribirse.isEnabled = b
    }
    private fun cancelSubscription() {
        Toast.makeText(
            activityParent,
            "Cancelar renovación automatica",
            Toast.LENGTH_SHORT
        ).show()
    }
}

