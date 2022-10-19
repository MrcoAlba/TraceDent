package the.goats.tracedent.views.fragments

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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.api.UserLoginResponse
import the.goats.tracedent.api.Usuario
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.UserPostLogin
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class LoginFragment
    : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity
    private lateinit var auth: FirebaseAuth
    private lateinit var service : RetrofitService



    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        service = Common.retrofitService
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "LoginFragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
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
        if (validateCredentials(email,password)){
            //Firebase authentication
            Authentication(email,password)
        }
    }

    private fun getUserAccountInfo(email: String,contrasena: String){
        service.getUserAccountInfo(UserPostLogin(email,contrasena)).enqueue(object: Callback<UserLoginResponse>{
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ){
                val user = (response.body() as UserLoginResponse)
                saveUserAccountInfoOnDevice(user)
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }
        })
    }

    private fun saveUserAccountInfoOnDevice(user: UserLoginResponse){
        val json = Gson().toJson(user)
        Log.d("DDDDDDDDDDDD",json)
        val prefs : SharedPreferences = activityParent.getSharedPreferences("app.TraceDent",0)
        prefs.edit().putString("usuario",json).apply()
    }
    private fun Authentication(email: String, password: String){
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, go to next activity
                    login.login2Main()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activityParent, "Las credenciales no son válida",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun validateCredentials(email: String, password: String): Boolean {
        // Validate email match the pattern and password is valid
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.count()>=6) {
            return true
        }else{
            Toast.makeText(activityParent, "Ingrese correctamente los campos solicitados",           Toast.LENGTH_SHORT).show()
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
}