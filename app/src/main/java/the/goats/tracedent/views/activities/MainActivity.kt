package the.goats.tracedent.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.MenuItem
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import the.goats.tracedent.R
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.Dentist
import the.goats.tracedent.api.Usuario
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.ApiService
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.MapFragment
import the.goats.tracedent.views.fragments.SearchFragment
import the.goats.tracedent.views.fragments.UsuarioFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    private val searchFragment = SearchFragment()
    private val principalFragments : List<Fragment> = listOf(
        SearchFragment(),
        MapFragment(),
        UsuarioFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(SearchFragment(), binding.fcvMainActivity)
        containerView = binding.fcvMainActivity

        // NAVIGATION BAR
        binding.bottomNavigationView.setOnItemSelectedListener { selectNavigationOption(it) }
    }

    private fun selectNavigationOption(menuItem: MenuItem) : Boolean{
        val ft = supportFragmentManager.beginTransaction()
        when (menuItem.itemId) {
            //Here goes the transitions between fragments, each lines performs when an item is pressed
            R.id.booking_item   -> Log.d("BtmNavView", "Se presiono para pasar a las reservas")
            R.id.search_item    -> ft.replace(binding.fcvMainActivity.id, principalFragments[0])
            R.id.map_item       -> ft.replace(binding.fcvMainActivity.id, principalFragments[1])
            R.id.profile_item   -> ft.replace(binding.fcvMainActivity.id, principalFragments[2])
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
    private fun GetRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tracedent-api.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun ChangesSubscription(){
        val retrofitBuilder = GetRetrofit()
        val apiService = retrofitBuilder.create(ApiService::class.java)
        val call = apiService.ChangeSuscription("afa16899-e6d5-4968-a522-f76bb947a0ee", true)
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                Log.i("onResponse","Se suscribio")
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}