package the.goats.tracedent.views.activities

import android.os.Bundle
import the.goats.tracedent.databinding.ActivityLoginBinding
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.RegisterG2Fragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(RegisterG2Fragment(), binding.fcvLoginActivity)
    }

}