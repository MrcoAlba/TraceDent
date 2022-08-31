package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.views.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic
        analyticEvent(fragmentActivity, "LoginFragment", "onViewCreated")
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        auth = Firebase.auth
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(fragmentActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        user?.run {
            Toast.makeText(fragmentActivity, this.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}