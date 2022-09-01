package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentForgottenPassword3Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class ForgottenPassword3Fragment : BaseFragment<FragmentForgottenPassword3Binding>(FragmentForgottenPassword3Binding::inflate) {

    lateinit var activityParent : LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic

        //Delegates
        communicator = activity as Communicator
        login = activity as Credential.LogIn

        activityParent =  activity as LoginActivity


        //Firebase Analytics
        analyticEvent(requireActivity(), "LoginFragment", "onViewCreated")
    }
}