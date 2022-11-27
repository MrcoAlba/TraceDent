package the.goats.tracedent.views.fragments.suscripcion

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentSuscripcion04Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.UsuarioFragment


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

        activityParent.back = false
        //Listeners
        binding.btnSuscribirse.setOnClickListener                       { continueSubscriptionLogic() }
    }

    //Selected option
    private fun continueSubscriptionLogic(){
        communicator
            .goToAnotherFragment(
                null,
                UsuarioFragment(),
                activityParent.containerView,
                "Suscripcion03FragmentUsuarioFragment"
            )

    }
}
