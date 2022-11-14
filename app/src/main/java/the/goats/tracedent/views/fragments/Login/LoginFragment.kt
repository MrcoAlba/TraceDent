package the.goats.tracedent.views.fragments.Login

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.OLDAPI.Login.Request.LoginPhase1
import the.goats.tracedent.api.OLDAPI.Login.Request.LoginPhase2
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase1.LoginResponsePhase1
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase1.LoginUserResponse
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Clinic.LoginPhase2ResponseClinic
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Dentist.LoginPhase2ResponseDentist
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Patient.LoginPhase2ResponsePatient
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.Register.RegisterG0Fragment

class LoginFragment
    : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity
    private lateinit var auth: FirebaseAuth
    private lateinit var mService : RetrofitService


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "LoginFragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
        mService = Common.retrofitService
        binding.btnLogin.setOnClickListener                     { login()               }
        binding.tvForgottenPassword.setOnClickListener          { forgottenPassword()   }
        binding.tvCreateAccount.setOnClickListener              { register()            }
    }

    //Login
    private fun login() {
        //User input
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        //Validate email and password
        if (validateCredentials(email,password)) {

            mService.logUser(LoginPhase1(email, password))
                .enqueue(object : Callback<LoginResponsePhase1> {
                    override fun onResponse(
                        call: Call<LoginResponsePhase1>,
                        response: Response<LoginResponsePhase1>
                    ) {
                        if (response.body() == null){
                            showErrorCredentials()
                            return
                        }

                        val response = response.body() as LoginResponsePhase1
                        processLogin(response,email)
                    }

                    override fun onFailure(call: Call<LoginResponsePhase1>, t: Throwable) {
                        // If sign in fails, display a message to the user.
                        showErrorCredentials()
                    }

                })
        }
    }

    private fun processLogin(response : LoginResponsePhase1, email: String){
        if(response.cod == 0 || response.response == null){
            showErrorCredentials()
            return
        }


        if(response.response.user_type == "patient"){
            loginPhase2Patient(response.response,email)
            return
        }

        if(response.response.user_type == "dentist"){
            loginPhase2Dentist(response.response,email)
            return
        }

        loginPhase2Clinic(response.response,email)

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

    //Mostrar Toast Credenciales erroneas
    private fun showErrorCredentials(){
        println("FAILLL")
        Toast.makeText(
            activityParent, "Las credenciales no son válidas",
            Toast.LENGTH_SHORT
        ).show()
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

    //Login fase 2 paciente
    private fun loginPhase2Patient(user : LoginUserResponse, email:String){
        mService.logPatient(LoginPhase2(user.id_user)).enqueue(object: Callback<LoginPhase2ResponsePatient>{

            override fun onResponse(
                call: Call<LoginPhase2ResponsePatient>,
                response: Response<LoginPhase2ResponsePatient>
            ) {

                if (response.body() == null){
                    showErrorCredentials()
                    return
                }

                val response = response.body() as LoginPhase2ResponsePatient

                if(response.cod == 0 || response.response == null){
                    showErrorCredentials()
                    return
                }

                val prefs = activityParent.getSharedPreferences(getString(R.string.Shared_Preferences),0)
                saveUserOnCellphone(user,email,prefs)

                prefs.edit().putString(getString(R.string.SP_Patient_id),response.response.id_patient).commit()

                login.login2Main()


            }

            override fun onFailure(call: Call<LoginPhase2ResponsePatient>, t: Throwable) {
                showErrorCredentials()
            }
        })
    }

    //Login fase 2 dentist
    private fun loginPhase2Dentist(user : LoginUserResponse, email:String){
        mService.logDentist(LoginPhase2(user.id_user)).enqueue(object: Callback<LoginPhase2ResponseDentist>{

            override fun onResponse(
                call: Call<LoginPhase2ResponseDentist>,
                response: Response<LoginPhase2ResponseDentist>
            ) {

                if (response.body() == null){
                    showErrorCredentials()
                    return
                }

                val response = response.body() as LoginPhase2ResponseDentist

                if(response.cod == 0 || response.response == null){
                    showErrorCredentials()
                    return
                }

                val prefs = activityParent.getSharedPreferences(getString(R.string.Shared_Preferences),0)
                saveUserOnCellphone(user,email,prefs)

                with(prefs.edit()){
                    putString(getString(R.string.SP_Dentist_id),response.response.id_dentist)
                    putString(getString(R.string.SP_Dentist_ruc),response.response.ruc)
                    putFloat(getString(R.string.SP_Dentist_rating),response.response.rating)
                    commit()
                }

                login.login2Main()


            }

            override fun onFailure(call: Call<LoginPhase2ResponseDentist>, t: Throwable) {
                showErrorCredentials()
            }
        })
    }

    //Login fase 2 clinic
    private fun loginPhase2Clinic(user : LoginUserResponse, email:String){
        mService.logClinic(LoginPhase2(user.id_user)).enqueue(object: Callback<LoginPhase2ResponseClinic>{

            override fun onResponse(
                call: Call<LoginPhase2ResponseClinic>,
                response: Response<LoginPhase2ResponseClinic>
            ) {

                if (response.body() == null){
                    showErrorCredentials()
                    return
                }

                val response = response.body() as LoginPhase2ResponseClinic

                if(response.cod == 0 || response.response == null){
                    showErrorCredentials()
                    return
                }

                val prefs = activityParent.getSharedPreferences(getString(R.string.Shared_Preferences),0)
                saveUserOnCellphone(user,email,prefs)

                with(prefs.edit()){
                    putString(getString(R.string.SP_Clinic_id),response.response.id_clinic)
                    putString(getString(R.string.SP_Clinic_ruc),response.response.ruc)
                    putString(getString(R.string.SP_Clinic_companyName),response.response.company_name)
                    putFloat(getString(R.string.SP_Clinic_rating),response.response.rating)
                    commit()
                }

                login.login2Main()


            }

            override fun onFailure(call: Call<LoginPhase2ResponseClinic>, t: Throwable) {
                showErrorCredentials()
            }
        })
    }


    private fun saveUserOnCellphone(user : LoginUserResponse, email:String, prefs : SharedPreferences){
        with(prefs.edit()){
            putString(getString(R.string.SP_idUsuario),user.id_user)
            putString(getString(R.string.SP_user_type),user.user_type)
            putString(getString(R.string.SP_mail),email)
            putString(getString(R.string.SP_direction),user.direction)
            putString(getString(R.string.SP_district),user.district)
            putLong(getString(R.string.SP_phonenumber),user.phone_number)
            putBoolean(getString(R.string.SP_estado_suscripcion),user.subscription)
            commit()
        }

    }

}