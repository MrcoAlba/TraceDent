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
    private var CVV = false
    private var Name = false
    private var LastName = false




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
        binding.tietCodigo.doAfterTextChanged                   { validateCVV() }
        binding.tietNombre.doAfterTextChanged                   { validateName() }
        binding.tietApellidos.doAfterTextChanged                { validateLastName() }

    }

    //Selected option
    private fun Suscription(){

    }
    private fun ValidateAllData(){
        if(Email == true && Card == true && CVV == true && Name == true && LastName == true){
            enableButton(true)
        }else{
            enableButton(false)
        }
    }
    private fun validateMailPattern(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validateMail(){
        val email = binding.tietEmail.text.toString()

        var icon : Int = R.drawable.ic_baseline_mail_outline_24
        Email = false
        if (validateMailPattern(email)){
            icon = R.drawable.ic_baseline_mark_email_read_24
            Email = true
        }
        binding.tilEmail.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun validateCradPattern(card: String): Boolean {
        return card.length ==16
    }
    private fun validateCard(){
        val card = binding.tietTarjeta.text.toString()

        var icon : Int = R.drawable.credit_card_24px
        Card = false
        if (validateCradPattern(card)){
            icon = R.drawable.credit_score_24px
            Card = true
        }
        binding.tilTarjeta.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun validateCVVPattern(CVV: String): Boolean {
        return CVV.length ==3
    }
    private fun validateCVV(){
        val cvv = binding.tietCodigo.text.toString()
        var icon : Int = R.drawable.credit_card_24px
        CVV = false
        if (validateCVVPattern(cvv)){
            icon = R.drawable.credit_score_24px
            CVV = true
        }
        binding.tilCodigo.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun validateName(){
        val name = binding.tietNombre.text.toString()
        var icon : Int = R.drawable.person_off_24px
        Name = false
        if (name.isNotEmpty()){
            icon = R.drawable.person_24px
            Name = true
        }
        binding.tilNombre.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun validateLastName(){
        val lastname = binding.tietApellidos.text.toString()
        var icon : Int = R.drawable.person_off_24px
        LastName = false
        if (lastname.isNotEmpty()){
            icon = R.drawable.person_24px
            LastName = true
        }
        binding.tilApellidos.setStartIconDrawable(icon)
        ValidateAllData()
    }
    private fun enableButton(b: Boolean) {
        binding.btnPagar.isClickable = b
        binding.btnPagar.isEnabled = b
    }
}
