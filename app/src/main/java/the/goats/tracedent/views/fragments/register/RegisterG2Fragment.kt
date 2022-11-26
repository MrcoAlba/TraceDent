package the.goats.tracedent.views.fragments.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import the.goats.tracedent.R
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentRegisterG2Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG2Fragment
    : BaseFragment<FragmentRegisterG2Binding>(FragmentRegisterG2Binding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        // Retrofit
        mService        =   Common.retrofitService
        //Listeners
        binding.tietPassword.doAfterTextChanged                 { validate1Password() }
        binding.tietPassword2.doAfterTextChanged                { validate2Password() }
        binding.btnCreateAccount.setOnClickListener             { createAccount() }
        binding.tvGoBack.setOnClickListener                     { activityParent.onBackPressed() }
    }

    //Validate password
    private fun validate1Password(){
        //User input
        val password = binding.tietPassword.text.toString()

        var icon : Int = R.drawable.key_off_24px
        //Validate password and password
        if (validatePasswordPattern(password)){
            icon = R.drawable.ic_baseline_vpn_key_24
        }

        binding.tilPassword.setStartIconDrawable(icon)
        validate2Password()
    }
    private fun validate2Password(){
        //User input
        val password = binding.tietPassword.text.toString()
        val password2 = binding.tietPassword2.text.toString()

        var icon : Int = R.drawable.key_off_24px
        enableButton(false)
        //Validate password and password
        if (validatePasswordPattern(password) && password==password2){
            icon = R.drawable.ic_baseline_vpn_key_24
            enableButton(true)
        }

        binding.tilPassword2.setStartIconDrawable(icon)
        binding.sivPassword.setImageResource(icon)
    }
    private fun validatePasswordPattern(password: String): Boolean {
        // Validate password with a valid one
        return password.length >=6
    }
    private fun enableButton(b: Boolean) {
        binding.btnCreateAccount.isClickable = b
        binding.btnCreateAccount.isEnabled = b
    }

    //Create account
    private fun createAccount(){
        val clientType : Int? = requireArguments().getInt("option")
        val email : String? = requireArguments().getString("mail")
        val password : String = binding.tietPassword2.text.toString()

        val bundle : Bundle = Bundle()
        bundle.putString("mail", email)
        bundle.putString("password", password)

        if (clientType != null && email != null) {
            when (clientType) {
                1 -> communicator.goToAnotherFragment(
                    bundle,
                    RegisterG3ClientFragment(),
                    activityParent.containerView,
                    "RegisterG22RegisterG5")
                2 -> communicator.goToAnotherFragment(
                    bundle,
                    RegisterG4DentistFragment(),
                    activityParent.containerView,
                    "RegisterG22RegisterG6")
                3 -> communicator.goToAnotherFragment(
                    bundle,
                    RegisterG5ClinicFragment(),
                    activityParent.containerView,
                    "RegisterG22RegisterG8")
            }
        }
    }
}