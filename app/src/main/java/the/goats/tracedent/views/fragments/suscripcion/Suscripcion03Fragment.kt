package the.goats.tracedent.views.fragments.suscripcion

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentSuscripcion03Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import java.util.*


class Suscripcion03Fragment
    : BaseFragment<FragmentSuscripcion03Binding>(FragmentSuscripcion03Binding::inflate)
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
        ChangeView()
    }
    private fun ChangeView(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                continueSubscriptionLogic()
            }
        }, 5000)
    }
    //Selected option
    private fun continueSubscriptionLogic(){
        communicator
            .goToAnotherFragment(
                null,
                Suscripcion04Fragment(),
                activityParent.containerView,
                "Suscripcion03FragmentSuscripcion04Fragment"
            )
    }
}
