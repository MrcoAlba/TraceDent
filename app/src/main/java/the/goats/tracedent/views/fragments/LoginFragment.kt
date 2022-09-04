package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class LoginFragment
    : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate)
{

    private lateinit var auth: FirebaseAuth
    lateinit var activityParent : LoginActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "LoginFragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
        binding.btnLogin.setOnClickListener             { login()               }
        binding.tvForgottenPassword.setOnClickListener  { forgottenPassword()   }
        binding.tvCreateAccount.setOnClickListener      { signUp()              }
    }

    //Login
    private fun login() {
        //User input
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        //Validate email and password
        if (validateCredentials(email,password)){
            //Firebase authenticati
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, go to next activity
                        login.login2Main()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activityParent, "Las credenciales no son válida",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun validateCredentials(email: String, password: String): Boolean {
        // Validate email is not empty
        if(email.isNotEmpty()){
            // Validate email address with a valid one
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Validate password is not empty
                if (password.isNotEmpty()){
                    // Validate password length is 6 or longer due to Firebase length constraints
                    if (password.count()>=6){
                        return true
                    }else{
                        Toast.makeText(activityParent, "Las credenciales no son válidas",           Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(activityParent, "Por favor ingrese una contraseña",              Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activityParent, "Por favor ingrese una dirección de correo válida",  Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(activityParent, "Por favor ingrese un correo",                           Toast.LENGTH_SHORT).show()
        }
        return false
    }

    //ForgottenPassword
    private fun forgottenPassword() {
        communicator
            .goToAnotherFragment(
                null,
                ForgottenPassword1Fragment(),
                activityParent.containerView,
                "Login2ForgottenPassword"
            )
    }

    //Register
    private fun signUp(){
        communicator
            .goToAnotherFragment(
                null,
                RegisterG0Fragment(),
                activityParent.containerView,
                "Login2RegisterG0"
            )
    }
}