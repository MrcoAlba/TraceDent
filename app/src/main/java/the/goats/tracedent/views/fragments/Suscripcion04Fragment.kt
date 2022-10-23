package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentSuscripcion03Binding
import the.goats.tracedent.databinding.FragmentSuscripcion04Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import java.util.*


class Suscripcion04Fragment
    : BaseFragment<FragmentSuscripcion04Binding>(FragmentSuscripcion04Binding::inflate)
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
        analyticEvent(requireActivity(), "Suscripcion04Fragment", "onViewCreated")


        //Listeners

        //binding.btnSuscribirse.setOnClickListener                     { activityParent.ChangesSubscription() }
        binding.btnSuscribirse.setOnClickListener                       { Continue(1) }
    }

    //Selected option
    private fun Continue(option:Int){
        val bundle : Bundle = Bundle()
        bundle.putInt("option", option)
        communicator
            .goToAnotherFragment(
                bundle,
                UsuarioFragment(),
                activityParent.containerView,
                "Suscripcion03FragmentUsuarioFragment"
            )

    }
}
