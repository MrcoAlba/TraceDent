package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.databinding.FragmentRegisterG2Binding
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG2Fragment : BaseFragment<FragmentRegisterG2Binding>(FragmentRegisterG2Binding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic

        //Code example to go from this view to another
        //communicator = view as MainActivity
        //communicator.goToAnotherFragment(bundle, fragment, containerView, transactionName)
    }

}