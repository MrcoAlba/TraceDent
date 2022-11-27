package the.goats.tracedent.views.fragments.suscripcion

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentSuscripcion02Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment


class Suscripcion02Fragment
    : BaseFragment<FragmentSuscripcion02Binding>(FragmentSuscripcion02Binding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as MainActivity
        // Retrofit
        mService        =   Common.retrofitService
        //Listeners
        binding.tietEmail.doAfterTextChanged                    { checkAllComplete() }
        binding.tietTarjeta.doAfterTextChanged                  { checkAllComplete() }
        binding.tietCodigo.doAfterTextChanged                   { checkAllComplete() }
        binding.tietNombre.doAfterTextChanged                   { checkAllComplete() }
        binding.tietApellidos.doAfterTextChanged                { checkAllComplete() }
        textWatcherForDateButton()
        binding.tietFecha.doAfterTextChanged                    { checkAllComplete() }
        binding.btnPagar.setOnClickListener                     { pay()              }
    }

    private fun textWatcherForDateButton() {
        binding.tietFecha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var current = s.toString()
                if (current.length == 2 && start == 1) {
                    binding.tietFecha.setText("$current/")
                    binding.tietFecha.setSelection(current.length + 1)
                } else if (current.length == 2 && before == 1) {
                    current = current.substring(0, 1)
                    binding.tietFecha.setText(current)
                    binding.tietFecha.setSelection(current.length)
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
    // Validate card
    private fun validateCard():Boolean{
        val card = binding.tietTarjeta.text.toString()
        binding.tilTarjeta.setStartIconDrawable(R.drawable.credit_card_24px)
        if (validateCardPattern(card)){
            binding.tilTarjeta.setStartIconDrawable(R.drawable.credit_score_24px)
            return true
        }
        return false
    }
    private fun validateCardPattern(card: String): Boolean {
        return card.length ==16
    }
    // Validate date
    private fun validateDate():Boolean{
        val date = binding.tietFecha.text.toString()
        binding.tilFecha.setStartIconDrawable(R.drawable.calendar_today_24px)
        if (validateDatePattern(date)){
            binding.tilFecha.setStartIconDrawable(R.drawable.calendar_month_24px)
            return true
        }
        return false
    }
    private fun validateDatePattern(date: String): Boolean {
        if (date.length >=5){
            val m = date.substring(0, 2).toInt()
            val y = date.substring(3, 5).toInt()
            if (m<=12 && y>=23) {
                return true
            }
        }
        return false
    }
    // Validate CVV
    private fun validateCVV():Boolean{
        val cvv = binding.tietCodigo.text.toString()
        binding.tilCodigo.setStartIconDrawable(R.drawable.credit_card_24px)
        if (validateCVVPattern(cvv)){
            binding.tilCodigo.setStartIconDrawable(R.drawable.credit_score_24px)
            return true
        }
        return false
    }
    private fun validateCVVPattern(CVV: String): Boolean {
        return CVV.length ==3
    }
    // Validate name
    private fun validateName():Boolean{
        val name = binding.tietNombre.text.toString()
        binding.tilNombre.setStartIconDrawable(R.drawable.person_off_24px)
        if (name.isNotEmpty()){
            binding.tilNombre.setStartIconDrawable(R.drawable.person_24px)
            return true
        }
        return false
    }
    // Validate last name
    private fun validateLastName():Boolean{
        val lastname = binding.tietApellidos.text.toString()
        binding.tilApellidos.setStartIconDrawable(R.drawable.person_off_24px)
        if (lastname.isNotEmpty()){
            binding.tilApellidos.setStartIconDrawable(R.drawable.person_24px)
            return true
        }
        return false
    }
    // Validate mail
    private fun validateMail():Boolean{
        val email = binding.tietEmail.text.toString()
        binding.tilEmail.setStartIconDrawable(R.drawable.ic_baseline_mail_outline_24)
        if (validateMailPattern(email)){
            binding.tilEmail.setStartIconDrawable(R.drawable.ic_baseline_mark_email_read_24)
            return true
        }
        return false
    }
    private fun validateMailPattern(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkAllComplete(){
        if(
            validateCard()      &&
            validateDate()      &&
            validateCVV()       &&
            validateName()      &&
            validateLastName()  &&
            validateMail()
        ) {
            enableButton(true)
        }
    }
    private fun enableButton(b: Boolean) {
        binding.btnPagar.isClickable = b
        binding.btnPagar.isEnabled = b
    }

    private fun pay() {
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        val id = prefs.getString(getString(R.string.sp_user_id),"0")
        changeSubscription(id!!)
    }
    private fun changeSubscription(id: String){
        mService.patchUserSubById(id).enqueue(object: Callback<ApiResponse<Int>> {
            override fun onResponse(
                call: Call<ApiResponse<Int>>,
                response: Response<ApiResponse<Int>>
            ) {
                continueSubscriptionLogic()
                saveSubscriptionOnCellphone()
            }

            override fun onFailure(call: Call<ApiResponse<Int>>, t: Throwable) {
                Toast.makeText(activityParent, "No se pudo realizar la transacción", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun continueSubscriptionLogic(){
        communicator
            .goToAnotherFragment(
                null,
                Suscripcion03Fragment(),
                activityParent.containerView,
                "Suscripcion02FragmentSuscripcion03Fragment"
            )
    }
    private fun saveSubscriptionOnCellphone(){
        with(activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),Context.MODE_PRIVATE)
            .edit()){
            putBoolean(getString(R.string.sp_subscription), true)
        }.commit()
    }
}

