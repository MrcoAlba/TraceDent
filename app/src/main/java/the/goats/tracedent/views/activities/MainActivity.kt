package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import the.goats.tracedent.R
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.MapFragment
import the.goats.tracedent.views.fragments.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    private val searchFragment = SearchFragment()
    private val principalFragments : List<Fragment> = listOf(
        SearchFragment(),
        MapFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(SearchFragment(), binding.fcvMainActivity)

        // NAVIGATION BAR
        binding.bottomNavigationView.setOnItemSelectedListener {
            val ft = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                //Here goes the transitions between fragments, each lines performs when an item is pressed
                R.id.booking_item -> Log.d("BtmNavView", "Se presiono para pasar a las reservas")
                R.id.search_item -> Move2Search(ft)
                R.id.map_item -> Move2Map(ft)
                R.id.profile_item -> Log.d("BtmNavView", "Se presiono para pasar al perfil")
                R.id.messaging_item -> Log.d("BtmNavView", "Se presiono para pasar a los chats")
                else -> {}
            }
            ft.addToBackStack(null)
            ft.commit()
            true
        }
    }

    private fun Move2Search(ft: FragmentTransaction) {
        ft.replace(R.id.fcv_main_activity, principalFragments[0])
    }

    private fun Move2Map(ft: FragmentTransaction) {
        ft.replace(R.id.fcv_main_activity, principalFragments[1])
    }

    override fun Main2Login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}