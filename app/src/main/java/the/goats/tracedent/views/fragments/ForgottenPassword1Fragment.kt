package the.goats.tracedent.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentForgottenPassword1Binding
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class ForgottenPassword1Fragment
    : BaseFragment<FragmentForgottenPassword1Binding>(FragmentForgottenPassword1Binding::inflate)
{

    private lateinit var auth: FirebaseAuth
    lateinit var activityParent : LoginActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        activityParent  =   requireActivity() as LoginActivity


        //Firebase Analytics
        analyticEvent(requireActivity(), "ForgottenPassword1Fragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
        binding.tietEmail.doAfterTextChanged                    { validateMail() }
        binding.btnSendLink.setOnClickListener                  { sendLink() }
        binding.tvGoBack.setOnClickListener                     { activityParent.onBackPressed() }
        previousEmail()
    }

    //Validate mail
    private fun validateMail(){
        //User input
        val email = binding.tietEmail.text.toString()
        enableButton(false)
        var icon : Int = R.drawable.ic_baseline_mail_outline_24
        //Validate email and password
        if (validateMailPattern(email)){
            icon = R.drawable.ic_baseline_mark_email_read_24
            enableButton(true)
        }

        binding.tilEmail.setStartIconDrawable(icon)
    }
    private fun validateMailPattern(email: String): Boolean {
        // Validate email address with a valid one
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun enableButton(b: Boolean) {
        binding.btnSendLink.isClickable = b
        binding.btnSendLink.isEnabled = b
    }

    private fun sendLink(){
        val mail : String = binding.tietEmail.text.toString()
        auth.sendPasswordResetEmail(mail).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                enableButton(false)
                binding.tvLinkInformation.setTextColor(Color.parseColor("#000000"))
                binding.tvLinkInformation.text = "El link al correo ha sido enviado"
            }else{
                binding.tvLinkInformation.setTextColor(Color.parseColor("#FF0000"))
                binding.tvLinkInformation.text = "No se ha enviado ningún correo, ingrese otro correo"
            }
        }
    }

    private fun previousEmail(){
        val email : String? = requireArguments().getString("email")
        if (email != null && email.isNotEmpty()) {
            binding.tietEmail.text = Editable.Factory.getInstance().newEditable(email)
        }
    }

}