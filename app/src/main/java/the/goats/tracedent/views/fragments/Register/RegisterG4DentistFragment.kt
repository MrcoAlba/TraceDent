package the.goats.tracedent.views.fragments.Register

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.api.Dentist
import the.goats.tracedent.api.Usuario
import the.goats.tracedent.api.Person
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
    private lateinit var auth: FirebaseAuth
    private lateinit var user : Usuario
    private lateinit var person: Person



    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG4DentistFragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
        binding.tietNombre.doAfterTextChanged               { CheckAllComplete() }
        binding.tietApellido.doAfterTextChanged             { CheckAllComplete() }
        binding.tietGenero.doAfterTextChanged               { CheckAllComplete() }
        binding.tietDni.doAfterTextChanged                  { CheckAllComplete() }
        binding.tietDireccion.doAfterTextChanged            { CheckAllComplete() }
        binding.tietNumeroContacto.doAfterTextChanged       { CheckAllComplete() }


        binding.buttonReturnG6.setOnClickListener           { activityParent.onBackPressed() }
        binding.butContinuarG6.setOnClickListener           {
            CreateDentist()
            confirmar() }
        //Lifecycle necessary functions
        CreateUser()
        CreatePerson()
    }

    private fun CreateUser(){
        val mail = requireArguments().getString("correo")
        val password = requireArguments().getString("password")
        val option = requireArguments().getInt("option")
        val phonenumber = binding.tietNumeroContacto.text.toString()
        val id = auth.currentUser?.uid.toString()
        val district = binding.tietDisitrito.text.toString().uppercase()
        val address = binding.tietDireccion.text.toString()
        user = Usuario(id
            , "dentist"
            , phonenumber.toLong()
            , false
            , district
            , address
            , 15.0
            , 15.0)
        activityParent.CreacionUsuario(user)
    }
    private fun CreatePerson() {
        val name = binding.tietNombre.text.toString()
        val id = auth.currentUser?.uid.toString()
        val lastname = binding.tietApellido.text.toString()
        val gender = binding.tietGenero.text.toString()
        val dni = binding.tietDni.text.toString()

        person = Person(id,name, lastname, gender, dni.toLong(), user)
    }

    private fun CreateDentist(){
        val mail = requireArguments().getString("correo").toString()
        val name = binding.tietNombre.text.toString()
        val lastname = binding.tietApellido.text.toString()
        val address = binding.tietDireccion.text.toString()
        val phonenumber = binding.tietNumeroContacto.text.toString().toInt()
        val gender = binding.tietGenero.text.toString()
        val dni = binding.tietDni.text.toString().toInt()
        val district = binding.tietDisitrito.text.toString().uppercase()
        val id = auth.currentUser?.uid.toString()
        val dentist = Dentist(id, dni.toString(), 0f, person)
        activityParent.CreateDentist(dentist)

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

        //communicator.goToAnotherFragment(bundle, RegisterG5DentistFragment(), activityParent.containerView, "RegisterG62RegisterG7")
        login.login2Main()
    }

    private fun CheckAllComplete(){
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