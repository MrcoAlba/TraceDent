package the.goats.tracedent.views.fragments.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.api.dentist.DentistCreated
import the.goats.tracedent.api.dentist.DentistCreation
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentRegisterG4DentistBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG4DentistFragment
    : BaseFragment<FragmentRegisterG4DentistBinding>(FragmentRegisterG4DentistBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        // Retrofit
        mService        =   Common.retrofitService
        //Listeners
        binding.tietNombre.doAfterTextChanged               { checkAllComplete() }
        binding.tietApellido.doAfterTextChanged             { checkAllComplete() }
        binding.tietGenero.doAfterTextChanged               { checkAllComplete() }
        binding.tietDni.doAfterTextChanged                  { checkAllComplete() }
        binding.tietDireccion.doAfterTextChanged            { checkAllComplete() }
        binding.tietNumeroContacto.doAfterTextChanged       { checkAllComplete() }
        binding.buttonReturnG6.setOnClickListener           { activityParent.onBackPressed() }
        binding.butContinuarG6.setOnClickListener           { createDentist()   }
    }

    private fun createDentist(){
        // Get variables
        val mail = requireArguments().getString("mail").toString()
        val pswd = requireArguments().getString("password").toString()
        val phoneNumber = binding.tietNumeroContacto.text.toString().toInt()
        val district = binding.tietDisitrito.text.toString().uppercase()
        val direction = binding.tietDireccion.text.toString()
        val latitude = 10F
        val longitude = 10F
        val firstName = binding.tietNombre.text.toString()
        val lastName = binding.tietApellido.text.toString()
        val gender = binding.tietGenero.text.toString()
        val dni = binding.tietDni.text.toString().toInt()
        val ruc = "001$dni"
        val dentist = DentistCreation(
            mail, pswd, phoneNumber, district, direction, latitude,
            longitude, firstName, lastName, gender, dni, ruc
        )
        mService.postDentist(dentist)
            .enqueue(object : Callback<ApiResponse<DentistCreated>> {
                override fun onResponse(
                    call: Call<ApiResponse<DentistCreated>>,
                    response: Response<ApiResponse<DentistCreated>>
                ) {
                    if (response.body()?.data?.get(0) is DentistCreated || response.body()?.message =="DENTIST CREATED"){
                        response.body()?.data?.get(0)?.id_dentist?.let { activityParent.saveUserTypeAndId(it, "dentist") }
                        login.login2Main()
                    }else{
                        Toast.makeText(activityParent, response.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<DentistCreated>>, t: Throwable) {
                    Toast.makeText(activityParent, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun checkAllComplete(){
        if(binding.tietNombre.text.toString() != "" && binding.tietApellido.text.toString() != ""
            && binding.tietDni.text.toString() != "" && binding.tietDireccion.text.toString() != "" &&
            binding.tietGenero.text.toString() != "" && binding.tietNumeroContacto.text.toString() != ""
            && validateDNIPattern(binding.tietDni.text.toString()) && validateNumberPattern(binding.tietNumeroContacto.text.toString())) {
            enableButton(true)
        }
    }
    private fun enableButton(b: Boolean) {
        binding.butContinuarG6.isClickable = b
        binding.butContinuarG6.isEnabled = b
    }
    private fun validateDNIPattern(dni: String): Boolean {
        // Validate dni with a valid one
        return dni.length == 8
    }
    private fun validateNumberPattern(number: String): Boolean {
        // Validate password with a valid one
        return number.length == 9
    }
}