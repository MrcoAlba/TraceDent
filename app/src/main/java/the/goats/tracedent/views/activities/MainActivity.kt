package the.goats.tracedent.views.activities

import android.os.Bundle
import android.util.Log
import the.goats.tracedent.R
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.LoginFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(LoginFragment(), binding.fcvMainActivity)


        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                //Here goes the transitions between fragments, each lines performs when an item is pressed

                R.id.booking_item -> Log.d("BtmNavView", "Se presiono para pasar a las reservas")
                R.id.search_item -> Log.d("BtmNavView", "Se presiono para pasar a la busqueda")
                R.id.map_item -> Log.d("BtmNavView", "Se presiono para pasar al mapa(principal")
                R.id.profile_item -> Log.d("BtmNavView", "Se presiono para pasar al perfil")
                R.id.messaging_item -> Log.d("BtmNavView", "Se presiono para pasar a los chats")

                else -> {}
            }

            true
        }
    }

}