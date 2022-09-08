package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.Api.DefaultResponse
import the.goats.tracedent.Api.RetrofitClient
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentRegisterG5Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment


class RegisterG5Fragment : BaseFragment<FragmentRegisterG5Binding>(FragmentRegisterG5Binding::inflate) {

    private lateinit var auth: FirebaseAuth
    lateinit var activityParent : LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity

        //Listeners

        binding.butConfirmarG5.setOnClickListener           {

            RetrofitClient.instance.CreateUser("123456654ds"
                , "pruebaRetro@gmail.com"
                , "1234567"
                , 1)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(activityParent, response.body()?.msj, Toast.LENGTH_SHORT).show()
                        Log.i("Hola", "Hola")
                    }
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(activityParent,t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            //confirmar()

        }
    }

    private fun confirmar() {
        val bundle : Bundle = Bundle()
        bundle.putString("nombre", binding.tietNombre.text.toString())
        bundle.putString("apellido", binding.tietApellido.text.toString())
        bundle.putString("dni", binding.tietDni.text.toString())
        bundle.putString("numero_contacto", binding.tietNumeroContacto.text.toString())
        bundle.putString("direccion", binding.tietDireccion.text.toString())
        bundle.putString("genero", binding.tietGenero.text.toString())
        bundle.putString("mail", requireArguments().getString("correo"))
        bundle.putString("password", requireArguments().getString("password"))
        bundle.putInt("option", requireArguments().getInt("option"))

        login.login2Main()
    }
}