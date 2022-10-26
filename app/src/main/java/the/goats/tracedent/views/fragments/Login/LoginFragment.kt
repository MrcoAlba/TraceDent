package the.goats.tracedent.views.fragments.Login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
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
import the.goats.tracedent.api.Login.Request.LoginRequest
import the.goats.tracedent.api.Login.Response.LoginUserResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.interfaces.ApiService
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.Login.ForgottenPassword1Fragment
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

            mService.logUser(LoginRequest(email, password))
                .enqueue(object : Callback<LoginUserResponse> {
                    override fun onResponse(
                        call: Call<LoginUserResponse>,
                        response: Response<LoginUserResponse>
                    ) {
                        saveUserOnCellphone((response.body() as LoginUserResponse))
                        login.login2Main()
                    }

                    override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            activityParent, "Las credenciales no son válidas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
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
    private fun saveUserOnCellphone(user : LoginUserResponse){
        val preferences = activityParent.getPreferences(Context.MODE_PRIVATE)

        with(preferences.edit()){
            putString(getString(R.string.SP_idUsuario),user.id_user)
            putString(getString(R.string.SP_user_type),user.user_type)
            putString(getString(R.string.SP_mail),user.mail)
            putBoolean(getString(R.string.SP_estado_suscripcion),user.subscription)
            commit()
        }

    }

}