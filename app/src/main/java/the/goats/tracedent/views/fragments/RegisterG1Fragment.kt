package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentRegisterG1Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG1Fragment
    : BaseFragment<FragmentRegisterG1Binding>(FragmentRegisterG1Binding::inflate)
{

    lateinit var activityParent : LoginActivity


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as LoginActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG1Fragment", "onViewCreated")


        //Listeners
        binding.tietEmail.doAfterTextChanged            { validate1Mail() }
        binding.tietEmail2.doAfterTextChanged           { validate2Mail() }
        binding.btnCreatePassword.setOnClickListener    { createPassword() }
        binding.tvGoBack.setOnClickListener             { activityParent.onBackPressed() }
    }

    private fun validate1Mail(){
        //User input
        val email = binding.tietEmail.text.toString()

        var icon : Int = R.drawable.ic_baseline_mail_outline_24
        //Validate email and password
        if (validateMailPattern(email)){
            icon = R.drawable.ic_baseline_mark_email_read_24
        }

        binding.tilEmail.setStartIconDrawable(icon)
        validate2Mail()
    }
    private fun validate2Mail(){
        //User input
        val email = binding.tietEmail.text.toString()
        val email2 = binding.tietEmail2.text.toString()

        var icon : Int = R.drawable.ic_baseline_mail_outline_24
        enableButton(false)
        //Validate email and password
        if (validateMailPattern(email) && email==email2){
            icon = R.drawable.ic_baseline_mark_email_read_24
            enableButton(true)
        }

        binding.tilEmail2.setStartIconDrawable(icon)
        binding.sivPassword.setImageResource(icon)
    }
    private fun validateMailPattern(email: String): Boolean {
        // Validate email address with a valid one
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun enableButton(b: Boolean) {
        binding.btnCreatePassword.isClickable = b
        binding.btnCreatePassword.isEnabled = b
    }

    private fun createPassword(){
        //Save in memory that client card view was pressed
        val bundle : Bundle = Bundle()
        //new bundle
        bundle.putString("mail", binding.tietEmail2.text.toString())
        //previous bundle
        bundle.putInt("option",requireArguments().getInt("option"))
        communicator
            .goToAnotherFragment(
                bundle,
                RegisterG2Fragment(),
                activityParent.containerView,
                "RegisterG12RegisterG2"
            )
    }
}
