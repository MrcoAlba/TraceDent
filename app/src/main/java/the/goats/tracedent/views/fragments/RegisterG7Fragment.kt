package the.goats.tracedent.views.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import the.goats.tracedent.databinding.FragmentRegisterG7Binding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment


class RegisterG7Fragment : BaseFragment<FragmentRegisterG7Binding>(FragmentRegisterG7Binding::inflate) {

    private lateinit var auth: FirebaseAuth
    lateinit var activityParent : LoginActivity

    private val SELECT_ACTIVITY = 50

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Delegates
        communicator    =   requireActivity() as Communicator
        login           =   requireActivity() as Credential.LogIn
        activityParent  =   requireActivity() as LoginActivity

        //Listeners

        binding.butAttachFile.setOnClickListener            { /*seleccionar()*/ }
        binding.butConfirmarG7.setOnClickListener           { /*confirmar()*/ }
    }


    private fun seleccionar() {
       var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
       intent.setType("image/")
       startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            var path : Uri = data?.getData()!!
            binding.imageView.setImageURI(path)
        }
    }

    private fun confirmar() {
        val bundle : Bundle = Bundle()
        bundle.putString("mail", requireArguments().getString("correo"))
        bundle.putString("password", requireArguments().getString("password"))
        bundle.putInt("option", requireArguments().getInt("option"))

        login.login2Main()
    }
}