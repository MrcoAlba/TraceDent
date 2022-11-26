package the.goats.tracedent.views.fragments.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.api.clinic.ClinicCreated
import the.goats.tracedent.api.clinic.ClinicCreation
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentRegisterG5ClinicBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG5ClinicFragment
    : BaseFragment<FragmentRegisterG5ClinicBinding>(FragmentRegisterG5ClinicBinding::inflate)
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
        binding.tietRsocial.doAfterTextChanged              { checkAllComplete() }
        binding.tietDir.doAfterTextChanged                  { checkAllComplete() }
        binding.tietRuc.doAfterTextChanged                  { checkAllComplete() }
        binding.tietDis.doAfterTextChanged                  { checkAllComplete() }
        binding.tietNum.doAfterTextChanged                  { checkAllComplete() }
        binding.butContinuarG6.setOnClickListener           { CreateClinic()     }
        binding.buttonReturnG8.setOnClickListener           { activityParent.onBackPressed() }
    }

    private fun CreateClinic(){
        // Get variables
        val mail = requireArguments().getString("mail").toString()
        val pswd = requireArguments().getString("password").toString()

        val companyName = binding.tietRsocial.text.toString()
        val ruc = binding.tietRuc.text.toString()
        val phoneNumber = binding.tietNum.text.toString().toInt()
        val district = binding.tietDir.text.toString()
        val direction = binding.tietDis.text.toString()
        val latitude = 10F
        val longitude = 10F
        val clinic = ClinicCreation(
            mail, pswd, phoneNumber, district, direction,
            latitude, longitude, companyName, ruc
        )
        mService.postClinic(clinic)
            .enqueue(object : Callback<ApiResponse<ClinicCreated>> {
                override fun onResponse(
                    call: Call<ApiResponse<ClinicCreated>>,
                    response: Response<ApiResponse<ClinicCreated>>
                ) {
                    if (response.body()?.data?.get(0) is ClinicCreated || response.body()?.message =="CLINIC CREATED"){
                        response.body()?.data?.get(0)?.id_clinic?.let { activityParent.saveUserTypeAndId(it, "clinic") }
                        login.login2Main()
                    }else{
                        Toast.makeText(activityParent, response.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<ClinicCreated>>, t: Throwable) {
                    Toast.makeText(activityParent, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun checkAllComplete(){
        if(
            binding.tietDir.text.toString() != ""               &&
            binding.tietDis.text.toString() != ""               &&
            binding.tietRuc.text.toString() != ""               &&
            binding.tietNum.text.toString() != ""               &&
            binding.tietRsocial.text.toString() != ""           &&
            validateRUCPattern(binding.tietRuc.text.toString())
        ) {
            enableButton(true)
        }
    }
    private fun enableButton(b: Boolean) {
        binding.butContinuarG6.isClickable = b
        binding.butContinuarG6.isEnabled = b
    }
    private fun validateRUCPattern(ruc: String): Boolean {
        // Validate ruc with a valid one
        return ruc.length == 11
    }
}