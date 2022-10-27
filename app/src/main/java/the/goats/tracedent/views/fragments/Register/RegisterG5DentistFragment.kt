package the.goats.tracedent.views.fragments.Register

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import the.goats.tracedent.databinding.FragmentRegisterG5DentistBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.base.BaseFragment

class RegisterG5DentistFragment
    : BaseFragment<FragmentRegisterG5DentistBinding>(FragmentRegisterG5DentistBinding::inflate)
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
        //Firebase Analytics
        analyticEvent(requireActivity(), "RegisterG5DentistFragment", "onViewCreated")


        //Listeners
        binding.butAttachFile.setOnClickListener            { /*select()*/ }
        binding.butConfirmarG7.setOnClickListener           { /*confirm()*/ }
        binding.buttonReturnG7.setOnClickListener           { activityParent.onBackPressed() }
        binding.butConfirmarG7.setOnClickListener           { confirm() }
    }


    private fun select() {
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

    private fun confirm() {
        val bundle : Bundle = Bundle()
        bundle.putString("mail", requireArguments().getString("correo"))
        bundle.putString("password", requireArguments().getString("password"))
        bundle.putInt("option", requireArguments().getInt("option"))

        login.login2Main()
    }
}