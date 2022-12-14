package the.goats.tracedent.views.fragments.Register

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.api.OLDAPI.Clinic
import the.goats.tracedent.api.OLDAPI.Usuario
import the.goats.tracedent.databinding.FragmentRegisterG6ClinicBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG6ClinicFragment
    : BaseFragment<FragmentRegisterG6ClinicBinding>(FragmentRegisterG6ClinicBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : LoginActivity
    private lateinit var auth: FirebaseAuth
    private lateinit var user : Usuario



    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG6ClinicFragment", "onViewCreated")
        //Firebase Auth
        auth = Firebase.auth
        //Listeners
        binding.tietRsocial.doAfterTextChanged              { CheckAllComplete() }
        binding.tietDir.doAfterTextChanged                  { CheckAllComplete() }
        binding.tietRuc.doAfterTextChanged                  { CheckAllComplete() }
        binding.tietDis.doAfterTextChanged                  { CheckAllComplete() }
        binding.tietNum.doAfterTextChanged                  { CheckAllComplete() }

        binding.butContinuarG6.setOnClickListener           {
            CreateClinic()
            confirmar()
            }
        binding.buttonReturnG8.setOnClickListener           { activityParent.onBackPressed() }
        //Lifecycle necessary functions
        CreateUser()
    }


    private fun CreateUser(){
        val mail = requireArguments().getString("correo")
        val password = requireArguments().getString("password")
        val option = requireArguments().getInt("option")
        val phonenumber = binding.tietNum.text.toString()
        val id = auth.currentUser?.uid.toString()
        val district = binding.tietDis.text.toString()
        val address = binding.tietDir.text.toString()
        user = Usuario(id,"clinic",phonenumber.toLong(),false,district, address, 15.0, 15.0)

        activityParent.CreacionUsuario(user)
    }
    private fun CreateClinic(){
        val mail = requireArguments().getString("correo").toString()
        val razon = binding.tietRsocial.text.toString()
        val ruc = binding.tietRuc.text.toString().toLong()
        val id = auth.currentUser?.uid.toString()
        val rating = 0f
        val clinic = Clinic(id, razon, ruc.toString(), rating, user)
        activityParent.CreateClinic(clinic)
    }

    private fun confirmar() {
        val bundle : Bundle = Bundle()
        login.login2Main()
    }

    private fun CheckAllComplete(){

        if(binding.tietDir.text.toString() != "" && binding.tietDis.text.toString() != ""
            && binding.tietRuc.text.toString() != "" && binding.tietNum.text.toString() != "" &&
            binding.tietRsocial.text.toString() != "" && validateRUCPattern(binding.tietRuc.text.toString())) {
            enableButton(true)
        }
    }

    private fun validateRUCPattern(ruc: String): Boolean {
        // Validate ruc with a valid one
        return ruc.length == 11
    }

    private fun enableButton(b: Boolean) {
        binding.butContinuarG6.isClickable = b
        binding.butContinuarG6.isEnabled = b
    }
}