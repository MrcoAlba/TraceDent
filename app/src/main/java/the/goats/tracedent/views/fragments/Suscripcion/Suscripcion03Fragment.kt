package the.goats.tracedent.views.fragments.Suscripcion

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

        //Firebase Analytics
        analyticEvent(requireActivity(), "Suscripcion03Fragment", "onViewCreated")
        //activityParent.onBackPressed()
        ChangeView()
        activityParent.back = true

        //Listeners

        //binding.btnSuscribirse.setOnClickListener                     { activityParent.ChangesSubscription() }
    }
    private fun ChangeView(){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Continue(1)
            }
        }, 3000)
    }
    //Selected option
    private fun Continue(option:Int){
        val bundle : Bundle = Bundle()
        bundle.putInt("option", option)
        communicator
            .goToAnotherFragment(
                bundle,
                Suscripcion04Fragment(),
                activityParent.containerView,
                "Suscripcion03FragmentSuscripcion04Fragment"
            )

    }
}
