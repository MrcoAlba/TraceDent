package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.views.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        
    }

}