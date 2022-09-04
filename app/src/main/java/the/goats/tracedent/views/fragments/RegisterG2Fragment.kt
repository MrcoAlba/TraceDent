package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.databinding.FragmentRegisterG2Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG2Fragment
    : BaseFragment<FragmentRegisterG2Binding>(FragmentRegisterG2Binding::inflate)
{

    lateinit var activityParent : LoginActivity


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as LoginActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG2Fragment", "onViewCreated")


        //Listeners
        binding.tietPassword.doAfterTextChanged            { validate1Password() }
        binding.tietPassword2.doAfterTextChanged           { validate2Password() }
        binding.btnCreateAccount.setOnClickListener  { createAccount() }
        binding.tvGoBack.setOnClickListener     { activityParent.onBackPressed() }
    }

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

    private fun createAccount(){
        val clientType : Int? = requireArguments().getInt("option")
        val email : String? = requireArguments().getString("mail")
        if (clientType != null && email != null) {
            // AUTH
            communicator
                .goToAnotherFragment(
                    null,
                    RegisterG3Fragment(),
                    activityParent.containerView,
                    "RegisterG22RegisterG3"
                )
        }else{
            Toast.makeText(activityParent, "Ha sucedido un error", Toast.LENGTH_SHORT).show()
            throw error("El tipo de cliente o el tipo de email es nulo")
        }


    }

}