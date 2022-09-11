package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentMapBinding
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


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        activityParent  =   requireActivity() as MainActivity


        //Firebase Analytics
        analyticEvent(requireActivity(), "BaseFragment", "onViewCreated")
        //Lifecycle necessary functions
        createFragment()
    }


    private fun createFragment(){
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        gmMap = googleMap
        createMarker()
    }
    private fun createMarker() {
        val coordinates = LatLng(-12.08546, -76.97122)
        val marker = MarkerOptions().position(coordinates).title("Mi ubicación")
        gmMap.addMarker(marker)
        gmMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),
            4000,
            null
        )
    }
}