package the.goats.tracedent.views.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentMapBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class MapFragment
    : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), OnMapReadyCallback
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private lateinit var gmMap : GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }



    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "LoginFragment", "onViewCreated")


        //Listeners

        //Fragment functions
        val gmMapFragment = activityParent.supportFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        gmMapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmMap = googleMap
    }

}