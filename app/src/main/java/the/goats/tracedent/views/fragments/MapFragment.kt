package the.goats.tracedent.views.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import the.goats.tracedent.R

class MapFragment
    : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener
{

    private lateinit var gmMap : GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val supportMapFragment = childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmMap = googleMap
        //createMarker()
        gmMap.setOnMyLocationButtonClickListener(this)
        enableLocation()
    }

    /*private fun createMarker() {
        val coordinates = LatLng()
        val marker = MarkerOptions().position(coordinates).title("Mi ubicación")
        gmMap.addMarker(marker)
        gmMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),
            4000,
            null
        )
    }*/

    private fun isLocationPermissionGranted() = ContextCompat
        .checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) ==PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::gmMap.isInitialized) return
        if(isLocationPermissionGranted()){
            gmMap.isMyLocationEnabled = true
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los terminos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat
                .requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                gmMap.isMyLocationEnabled = true
            }else{
                Toast.makeText(requireContext(), "Ve a ajustes y acepta los terminos", Toast.LENGTH_SHORT).show()
            }
            else -> { }
        }
    }

    override fun onResume() {
        super.onResume()
        if(!::gmMap.isInitialized) return
        if(!isLocationPermissionGranted()){
            gmMap.isMyLocationEnabled = false
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los terminos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }
}