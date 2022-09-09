package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
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
            activityParent.Prueba()
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