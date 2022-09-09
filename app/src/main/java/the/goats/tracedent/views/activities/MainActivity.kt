package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.LoginFragment
import the.goats.tracedent.views.fragments.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(SearchFragment(), binding.fcvMainActivity)
        containerView = binding.fcvMainActivity
    }

    override fun Main2Login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}