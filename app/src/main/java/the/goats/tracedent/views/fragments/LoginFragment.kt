package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic

        //Firebase Analytics
        analyticEvent(fragmentActivity, "LoginFragment", "onViewCreated")

        //Firebase Auth
        auth = Firebase.auth

        //Button listeners
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(fragmentActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun updateUI() {
        //Go to next activity
        communicator.goToAnotherActivity(null, MainActivity())
    }

}