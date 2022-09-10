package the.goats.tracedent.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.R
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
    private lateinit var auth: FirebaseAuth



    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG2Fragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
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
        if (clientType != null && email != null) {
            // AUTH
            auth
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val bundle : Bundle = Bundle()
                        bundle.putString("correo", requireArguments().getString("mail"))
                        bundle.putString("password", binding.tietPassword.text.toString())
                        bundle.putInt("option", requireArguments().getInt("option"))

                        if(requireArguments().getInt("option") == 1) {
                            communicator.goToAnotherFragment(
                                bundle,
                                RegisterG5Fragment(),
                                activityParent.containerView,
                                "RegisterG22RegisterG5"
                            )
                        }
                        else if(requireArguments().getInt("option") == 2){
                            communicator.goToAnotherFragment(
                                bundle,
                                RegisterG6Fragment(),
                                activityParent.containerView,
                                "RegisterG22RegisterG6"
                            )
                        }
                        else if(requireArguments().getInt("option") == 3) {
                            communicator.goToAnotherFragment(
                                bundle,
                                RegisterG8Fragment(),
                                activityParent.containerView,
                                "RegisterG22RegisterG8"
                            )
                        }
                    }else if(task.exception?.message == "The email address is already in use by another account."){
                        Toast.makeText(activityParent, "Alguien acaba de crear una cuenta con el correo utilizado, regrese y utilice otro por favor", Toast.LENGTH_LONG).show()
                        binding.tvPasswordInformation.text = "Regrese y utilice otro correo por favor"
                        binding.tvPasswordInformation.setTextColor(Color.parseColor("#FF0000"))
                    }else{
                        Toast.makeText(activityParent, "No se ha podido crear la cuenta", Toast.LENGTH_LONG).show()
                        binding.tvPasswordInformation.text = "Utilice otra contraseña"
                        binding.tvPasswordInformation.setTextColor(Color.parseColor("#FF0000"))
                    }
                }
        }else{
            Toast.makeText(activityParent, "Ha sucedido un error", Toast.LENGTH_SHORT).show()
            throw error("El tipo de cliente o el tipo de email es nulo")
        }
    }
}