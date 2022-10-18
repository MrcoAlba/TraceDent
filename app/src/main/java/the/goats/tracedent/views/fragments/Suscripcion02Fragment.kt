package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentSuscripcion02Binding
import the.goats.tracedent.databinding.FragmentUsuarioBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class Suscripcion02Fragment
    : BaseFragment<FragmentSuscripcion02Binding>(FragmentSuscripcion02Binding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private var Email = false
    private var Card = false




    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "Suscripcion02Fragment", "onViewCreated")


        //Listeners
        binding.tietEmail.doAfterTextChanged                    { validateMail() }
        binding.tietTarjeta.doAfterTextChanged                  { validateCard() }

    }

    //Selected option
    private fun Suscription(){

    }
    private fun ValidateAllData(){
        if(Email == true && Card == true){
            enableButton(true)
        }
    }
    private fun validateMailPattern(email: String): Boolean {
        // Validate email address with a valid one
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validateMail(){
        //User input
        val email = binding.tietEmail.text.toString()

        var icon : Int = R.drawable.ic_baseline_mail_outline_24
        //Validate email
        if (validateMailPattern(email)){
            icon = R.drawable.ic_baseline_mark_email_read_24
            Email = true

        }
        binding.tilEmail.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun validateCradPattern(card: String): Boolean {
        // Validate card with a valid one
        return card.length ==16
    }
    private fun validateCard(){
        //User input
        val card = binding.tietTarjeta.text.toString()

        var icon : Int = R.drawable.credit_card_24px
        //Validate password and password
        if (validateCradPattern(card)){
            icon = R.drawable.credit_score_24px
            Card = true
        }

        binding.tilTarjeta.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun enableButton(b: Boolean) {
        binding.btnPagar.isClickable = b
        binding.btnPagar.isEnabled = b
    }
}
