package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import the.goats.tracedent.databinding.FragmentForgottenPassword1Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class ForgottenPassword1Fragment : BaseFragment<FragmentForgottenPassword1Binding>(FragmentForgottenPassword1Binding::inflate) {

    private lateinit var auth: FirebaseAuth
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