package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun Main2Login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}