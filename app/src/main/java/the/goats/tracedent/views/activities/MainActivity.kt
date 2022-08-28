package the.goats.tracedent.views.activities

import android.os.Bundle
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.LoginFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(LoginFragment(), binding.fcvMainActivity)

    }

}