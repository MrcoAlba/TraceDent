package the.goats.tracedent.views.fragments.register

import android.graphics.Color
import android.os.Bundle
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
import the.goats.tracedent.databinding.FragmentRegisterG1Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG1Fragment
    : BaseFragment<FragmentRegisterG1Binding>(FragmentRegisterG1Binding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent  =   requireActivity() as LoginActivity
        // Retrofit
        mService        =   Common.retrofitService
        //Listeners
        binding.tietEmail.doAfterTextChanged                    { validate1Mail() }
        binding.tietEmail2.doAfterTextChanged                   { validate2Mail() }
        binding.btnCreatePassword.setOnClickListener            { createPassword() }
        binding.tvGoBack.setOnClickListener                     { activityParent.onBackPressed() }
    }

    //Validate mail
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

    //Create password
    private fun createPassword(){
        val email = binding.tietEmail2.text.toString()
        validateMailExistence(email)
    }
    private fun validateMailExistence(email: String) {
        mService.emailCheckExistance(email)
            .enqueue(object : Callback<ApiResponse<Int>>{
                override fun onResponse(
                    call: Call<ApiResponse<Int>>,
                    response: Response<ApiResponse<Int>>
                ) {
                    if (response.body()?.data?.get(0) == 0){
                        moveNextFragment(email)
                    }else{
                        Toast.makeText(activityParent, "El correo está siendo usado por otro usuario", Toast.LENGTH_SHORT).show()
                        binding.tvKeyInformation.text = "Ingrese otro correo"
                        binding.tvKeyInformation.setTextColor(Color.parseColor("#FF0000"))
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Int>>, t: Throwable) {
                    Toast.makeText(activityParent, "Hay un error en la aplicación", Toast.LENGTH_SHORT).show()
                    binding.tvKeyInformation.text = "Ingrese otro correo"
                    binding.tvKeyInformation.setTextColor(Color.parseColor("#FF0000"))
                }
            })
    }
    private fun moveNextFragment(email : String) {
        //Save in memory that client card view was pressed
        val bundle : Bundle = Bundle()
        //new bundle
        bundle.putString("mail", email)
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
