package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import the.goats.tracedent.R
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.AppointmentFragment
import the.goats.tracedent.views.fragments.MapFragment
import the.goats.tracedent.views.fragments.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    private val searchFragment = AppointmentFragment()
    private val principalFragments : List<Fragment> = listOf(
        MapFragment(),
        SearchFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transactionFirstAndMainFragment(MapFragment(), binding.fcvMainActivity)

        containerView = binding.fcvMainActivity

        // NAVIGATION BAR
        binding.bottomNavigationView.setOnItemSelectedListener { selectNavigationOption(it) }
    }

    private fun selectNavigationOption(menuItem: MenuItem) : Boolean{
        val ft = supportFragmentManager.beginTransaction()
        when (menuItem.itemId) {
            //Here goes the transitions between fragments, each lines performs when an item is pressed
            R.id.booking_item   -> Log.d("BtmNavView", "Se presiono para pasar a las reservas")
            R.id.search_item    -> ft.replace(binding.fcvMainActivity.id, principalFragments[1])
            R.id.map_item       -> ft.replace(binding.fcvMainActivity.id, principalFragments[0])
            R.id.profile_item   -> Log.d("BtmNavView", "Se presiono para pasar al perfil")
            R.id.messaging_item -> Log.d("BtmNavView", "Se presiono para pasar a los chats")
            else -> {}
        }
        ft.addToBackStack(null)
        ft.commit()
        return true
    }

    override fun Main2Login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}