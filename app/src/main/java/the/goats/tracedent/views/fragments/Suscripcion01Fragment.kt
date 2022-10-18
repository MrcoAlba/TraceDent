package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentRegisterG0Binding
import the.goats.tracedent.databinding.FragmentSuscripcion01Binding
import the.goats.tracedent.databinding.FragmentUsuarioBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class Suscripcion01Fragment
    : BaseFragment<FragmentSuscripcion01Binding>(FragmentSuscripcion01Binding::inflate)
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

        //Firebase Analytics
        analyticEvent(requireActivity(), "Suscripcion01Fragment", "onViewCreated")


        //Listeners
        binding.btnSuscribirse.setOnClickListener                     { Continue(1) }
    }

    //Selected option
    private fun Continue(option:Int){
        //Save in memory that client card view was pressed
        val bundle : Bundle = Bundle()
        bundle.putInt("option", option)
        communicator
            .goToAnotherFragment(
                bundle,
                Suscripcion02Fragment(),
                activityParent.containerView,
                "Suscripcion01FragmentSuscripcion02Fragment"
            )
    }
}
