package the.goats.tracedent.views.activities

import android.os.Bundle
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.views.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Use next function to set first Fragment of the Activity right
        //transactionFirstAndMainFragment()

        //binding is already done by inherit from BaseActivity
    }

}