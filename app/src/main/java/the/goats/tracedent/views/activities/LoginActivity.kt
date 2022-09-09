package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import the.goats.tracedent.Api.DefaultResponse
import the.goats.tracedent.Api.Dentist
import the.goats.tracedent.Api.Patient
import the.goats.tracedent.Api.Usuario
import the.goats.tracedent.databinding.ActivityLoginBinding
import the.goats.tracedent.interfaces.ApiService
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.LoginFragment
import the.goats.tracedent.views.fragments.RegisterG5Fragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), Credential.LogIn {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(LoginFragment(), binding.fcvLoginActivity)
        containerView = binding.fcvLoginActivity
    }

    override fun login2Main() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun GetRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://tracedent-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun CreacionUsuario(User: Usuario){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tracedent-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofitBuilder.create(ApiService::class.java)
        val call = apiService.InserUsert(User)
        call.enqueue(object : Callback<DefaultResponse>{
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se creo el usuario")
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }
        })
    }
    fun CreatePatient(patient: Patient){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tracedent-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofitBuilder.create(ApiService::class.java)
        val call = apiService.InserPatient(patient)
        call.enqueue(object : Callback<DefaultResponse>{
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se creo el paciente")
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }
        })
    }
    fun CreateDentist(dentist: Dentist){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tracedent-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofitBuilder.create(ApiService::class.java)
        val call = apiService.InserDentist(dentist)
        call.enqueue(object : Callback<DefaultResponse>{
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se creo el dentista")
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }
        })
    }

}
