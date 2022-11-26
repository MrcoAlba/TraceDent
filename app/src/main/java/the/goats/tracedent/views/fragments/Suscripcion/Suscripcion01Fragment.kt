package the.goats.tracedent.views.fragments.Suscripcion

import android.content.Context
import android.os.Bundle
import android.util.Log
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
        val suscripcion = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),Context.MODE_PRIVATE).getBoolean(getString(R.string.sp_subscription),false)
        Log.i("Hola", suscripcion.toString())
        if (suscripcion == true) {
            enableButton(false)
            binding.btnSuscribirse.text = "Usted ya esta suscrito"
            binding.tvCancelarSusripcion.visibility = View.VISIBLE
        } else {
            enableButton(true)
            binding.btnSuscribirse.text = "Suscribirse"
            binding.tvCancelarSusripcion.visibility = View.GONE
        }
        //Listeners
        //binding.btnSuscribirse.setOnClickListener                     { activityParent.ChangesSubscription() }
        binding.btnSuscribirse.setOnClickListener { Continue(1) }
        binding.tvCancelarSusripcion.setOnClickListener {
            Toast.makeText(
                activityParent,
                "Cancelar renovación automatica",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun Continue(option: Int) {
        //Save in memory that client card view was pressed
        val bundle: Bundle = Bundle()
        bundle.putInt("option", option)
        communicator
            .goToAnotherFragment(
                bundle,
                Suscripcion02Fragment(),
                activityParent.containerView,
                "Suscripcion01FragmentSuscripcion02Fragment"
            )
    }

    private fun enableButton(b: Boolean) {
        binding.btnSuscribirse.isClickable = b
        binding.btnSuscribirse.isEnabled = b
    }
}

