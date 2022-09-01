package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import the.goats.tracedent.databinding.ActivityLoginBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.LoginFragment

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

}