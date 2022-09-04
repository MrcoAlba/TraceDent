package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentRegisterG0Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG0Fragment
    : BaseFragment<FragmentRegisterG0Binding>(FragmentRegisterG0Binding::inflate)
{

    lateinit var activityParent : LoginActivity


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as LoginActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG0Fragment", "onViewCreated")


        //Listeners
        binding.cvClient.setOnClickListener         { clientPressed(1) }
        binding.cvDentist.setOnClickListener        { clientPressed(2) }
        binding.cvClinic.setOnClickListener         { clientPressed(3) }
        binding.cvFranchice.setOnClickListener      { clientPressed(4) }
        binding.tvGoBack.setOnClickListener         { activityParent.onBackPressed() }
    }

    //Selected option
    private fun clientPressed(option:Int){
        //Save in memory that client card view was pressed
        val bundle : Bundle = Bundle()
        bundle.putInt("option", option)
        communicator
            .goToAnotherFragment(
                bundle,
                RegisterG1Fragment(),
                activityParent.containerView,
                "RegisterG02RegisterG1"
            )
    }
}
