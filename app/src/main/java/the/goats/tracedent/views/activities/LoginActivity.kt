package the.goats.tracedent.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.api.clinic.ClinicCreated
import the.goats.tracedent.api.clinic.ClinicCreation
import the.goats.tracedent.api.dentist.DentistCreated
import the.goats.tracedent.api.dentist.DentistCreation
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.ActivityLoginBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.model.User
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.login.LoginFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), Credential.LogIn {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrofit
        mService        =   Common.retrofitService
        checkIfUserExists()
        transactionFirstAndMainFragment(LoginFragment(), binding.fcvLoginActivity)
        containerView = binding.fcvLoginActivity
    }

    /**
     * This method checks if the logged user who's information is in the SharePreferences is valid.
     * If it's valid, the app is gonna sign in and move to the MainActivity.
     */
    private fun checkIfUserExists() {
        val preferences =  getSharedPreferences(getString(R.string.sp_shared_preferences),Context.MODE_PRIVATE)
        if (preferences.getString(getString(R.string.sp_user_id),null) != null) {
            login2Main()
        }
    }
    /**
     * Moves from the LoginActivity to the MainActivity
     */
    override fun login2Main() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun createDentist(dentist: DentistCreation){
        mService.insertDentist(dentist)
            .enqueue(object: Callback<ApiResponse<DentistCreated>>{
            override fun onResponse(
                call: Call<ApiResponse<DentistCreated>>,
                response: Response<ApiResponse<DentistCreated>>
            ) {
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se creo el dentista")
            }
            override fun onFailure(call: Call<ApiResponse<DentistCreated>>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun createClinic(clinic: ClinicCreation){
        mService.insertClinic(clinic)
            .enqueue(object : Callback<ApiResponse<ClinicCreated>>{
            override fun onResponse(
                call: Call<ApiResponse<ClinicCreated>>,
                response: Response<ApiResponse<ClinicCreated>>
            ) {
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se creo la clinica")
            }

            override fun onFailure(call: Call<ApiResponse<ClinicCreated>>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }






    // LOGIN FUNCTIONS

    fun saveUserOnCellphone(user : User, email:String){
        with(this.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
            .edit()){
            putString(getString(the.goats.tracedent.R.string.sp_user_id),user.id_user)
            putString(getString(the.goats.tracedent.R.string.sp_user_type),user.user_type)
            putString(getString(the.goats.tracedent.R.string.sp_mail),email)
            putString(getString(the.goats.tracedent.R.string.sp_direction),user.direction)
            putString(getString(the.goats.tracedent.R.string.sp_district),user.district)
            putLong(getString(the.goats.tracedent.R.string.sp_phone_number),user.phone_number!!)
            putBoolean(getString(the.goats.tracedent.R.string.sp_subscription),user.subscription!!)
        }.commit()
    }


    // REGISTER FUNCTIONS
    fun saveUserTypeAndId(idPatient : String, userType: String) {
        with(this.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
            .edit()){
            putString(getString(the.goats.tracedent.R.string.sp_patient_id),idPatient)
            putString(getString(the.goats.tracedent.R.string.sp_user_type),userType)
        }.commit()
    }

}
