package the.goats.tracedent.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentRegisterG5Binding
import the.goats.tracedent.databinding.FragmentRegisterG6Binding
import the.goats.tracedent.databinding.FragmentRegisterG8Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment


class RegisterG8Fragment : BaseFragment<FragmentRegisterG8Binding>(FragmentRegisterG8Binding::inflate) {

    private lateinit var auth: FirebaseAuth
    lateinit var activityParent : LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity

        //Listeners

        binding.butContinuarG6.setOnClickListener           { confirmar() }
        binding.buttonReturnG8.setOnClickListener           { activityParent.onBackPressed() }
    }

    private fun confirmar() {
        val bundle : Bundle = Bundle()
        communicator.goToAnotherFragment(bundle, RegisterG7Fragment(), activityParent.containerView, "RegisterG62RegisterG7")
    }
}