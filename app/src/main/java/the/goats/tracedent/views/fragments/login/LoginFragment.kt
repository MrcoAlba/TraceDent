package the.goats.tracedent.views.fragments.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.api.clinic.ClinicLoginIdUser
import the.goats.tracedent.api.dentist.DentistLoginIdUser
import the.goats.tracedent.api.patient.PatientLoginIdUser
import the.goats.tracedent.api.user.UserLoginPhase1
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Patient
import the.goats.tracedent.model.User
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.register.RegisterG0Fragment

class LoginFragment
    : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate)
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
        binding.btnLogin.setOnClickListener                     {
            login(binding.tietEmail.text.toString(),binding.tietPassword.text.toString())
        }
        binding.tvForgottenPassword.setOnClickListener          { forgottenPassword()   }
        binding.tvCreateAccount.setOnClickListener              { register()            }
    }

    //Login
    private fun login(email: String, password: String) {
        //User input
        val email = email
        val password = password
        //Validate email and password
        if (validateCredentials(email,password)) {
            mService.loginMailPass(UserLoginPhase1(email, password))
                .enqueue(object : Callback<ApiResponse<User>>{
                    override fun onResponse(
                        call: Call<ApiResponse<User>>,
                        response: Response<ApiResponse<User>>
                    ) {
                        try {
                            Log.e("TRABAJANDO",response.body()!!.toString())
                            if (response.body()?.data is List<User>){
                                processLogin(response.body()!!,email)
                            }else{
                                showErrorCredentials()
                                Log.e("HOLAAA ARRIBA",response.body()!!.toString())
                            }
                        }catch (e: Exception){
                                Toast.makeText(activityParent.baseContext,
                                    response.message(),
                                    Toast.LENGTH_SHORT)
                                    .show()
                                println(response.message())
                                println(e.message)

                        }


                    }
                    override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                        // If sign in fails, display a message to the user.
                        Log.e("HOLAAA ARRIBA",t.toString())
                        showErrorCredentials()
                    }
                })
        }
    }

    private fun processLogin(response : ApiResponse<User>, email: String){
        if(response.message == "OK" ){
            when (response.data[0].user_type){
                "dentist"   -> loginPhase2Dentist(response.data[0],email)
                "clinic"    -> loginPhase2Clinic(response.data[0],email)
                else        -> loginPhase2Patient(response.data[0],email)
            }
        }else{
            showErrorCredentials()
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        // Validate email is not empty
        if(email.isNotEmpty()){
            // Validate email address with a valid one
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Validate password is not empty
                if (password.isNotEmpty()){
                    // Validate password length is 6 or longer due to Firebase length constraints
                    if (password.count()>=6){
                        return true
                    }else{
                        Toast.makeText(activityParent, "Las credenciales no son válidas",           Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(activityParent, "Por favor ingrese una contraseña",              Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activityParent, "Por favor ingrese una dirección de correo válida",  Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(activityParent, "Por favor ingrese un correo",                           Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun showErrorCredentials(){
        Toast.makeText(activityParent, "Las credenciales no son válidas", Toast.LENGTH_SHORT).show()
    }

    //ForgottenPassword
    private fun forgottenPassword() {
        //Save in memory that client card view was pressed
        val email : String = binding.tietEmail.text.toString()
        val bundle : Bundle = Bundle()
        bundle.putString("email", email)
        communicator
            .goToAnotherFragment(
                bundle,
                ForgottenPassword1Fragment(),
                activityParent.containerView,
                "Login2ForgottenPassword"
            )
    }

    //Register
    private fun register()  {
        communicator
            .goToAnotherFragment(
                null,
                RegisterG0Fragment(),
                activityParent.containerView,
                "Login2RegisterG0"
            )
    }

    //Login fase 2 patient
    private fun loginPhase2Patient(user : User, email:String){
        mService.patientLoginByIdUser(PatientLoginIdUser(user.id_user))
            .enqueue(object: Callback<ApiResponse<Patient>>{
                override fun onResponse(
                    call: Call<ApiResponse<Patient>>,
                    response: Response<ApiResponse<Patient>>
                ) {
                    if (response.body()?.data is List<Patient>){
                        activityParent.saveUserOnCellphone(user,email)
                        // Save the patient on cellphone
                        activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                            .edit()
                            .putString(getString(R.string.sp_patient_id),response.body()!!.data[0].id_patient)
                            .commit()

                        login.login2Main()
                    }else{
                        showErrorCredentials()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Patient>>, t: Throwable) {
                    showErrorCredentials()
                }
            })
    }
    //Login fase 2 dentist
    private fun loginPhase2Dentist(user : User, email:String){
        mService.dentistLoginByIdUser(DentistLoginIdUser(user.id_user))
            .enqueue(object : Callback<ApiResponse<Dentist>>{
                override fun onResponse(
                    call: Call<ApiResponse<Dentist>>,
                    response: Response<ApiResponse<Dentist>>
                ) {
                    if (response.body()?.data is List<Dentist>){
                        activityParent.saveUserOnCellphone(user,email)
                        // Save the patient on cellphone
                        activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                            .edit()
                            .putString(getString(R.string.sp_dentist_id),response.body()!!.data[0].id_dentist)
                            .putString(getString(R.string.sp_dentist_ruc),response.body()!!.data[0].ruc)
                            .putFloat(getString(R.string.sp_dentist_rating),response.body()!!.data[0].rating!!)
                            .commit()

                        login.login2Main()
                    }else{
                        showErrorCredentials()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                    showErrorCredentials()
                }
            })
    }
    //Login fase 2 clinic
    private fun loginPhase2Clinic(user : User, email:String){
        mService.clinicLoginByIdUser(ClinicLoginIdUser(user.id_user))
            .enqueue(object: Callback<ApiResponse<Clinic>>{
                override fun onResponse(
                    call: Call<ApiResponse<Clinic>>,
                    response: Response<ApiResponse<Clinic>>
                ) {
                    if (response.body()?.data is List<Clinic>){
                        activityParent.saveUserOnCellphone(user,email)
                        // Save the patient on cellphone
                        activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                            .edit()
                            .putString(getString(R.string.sp_clinic_id),response.body()!!.data[0].id_clinic)
                            .putString(getString(R.string.sp_clinic_ruc),response.body()!!.data[0].ruc)
                            .putString(getString(R.string.sp_clinic_company_name),response.body()!!.data[0].company_name)
                            .putFloat(getString(R.string.sp_clinic_rating),response.body()!!.data[0].rating!!)
                            .commit()

                        login.login2Main()
                    }else{
                        showErrorCredentials()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Clinic>>, t: Throwable) {
                    showErrorCredentials()
                }
            })
    }




}