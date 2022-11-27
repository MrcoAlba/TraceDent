package the.goats.tracedent.views.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentMapBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Person
import the.goats.tracedent.model.User
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment


class MapFragment
    : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate)
    , OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener
    , GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener
    , GoogleMap.OnMarkerClickListener
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private lateinit var gmMap : GoogleMap

    private  var latitude : Double = 0.0
    private  var longitude : Double = 0.0

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient


    companion object {
        const val REQUEST_CODE_LOCATION = 0
        val camera_zoom = 15
    }

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        // Retrofit
        mService = Common.retrofitService

        //Lifecycle necessary functions
        createFragment()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activityParent)

        enableLocation()

    }

    private fun createFragment(){
        try {
            val supportMapFragment = childFragmentManager
                .findFragmentById(R.id.f_map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
        }catch (e: Exception) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        gmMap = googleMap
        //createMarker()
        gmMap.setOnMyLocationButtonClickListener(this)
        gmMap.setOnMyLocationClickListener(this)
        enableLocation()
        //gmMap.setOnMapLongClickListener(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if(it != null) {
                val ubicacion = LatLng(it.latitude, it.longitude)
                latitude = it.latitude
                longitude = it.longitude
                gmMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 18f))
            }
        }

        gmMap.setOnMarkerClickListener(this)
        gmMap.uiSettings.isZoomControlsEnabled = true
        gmMap.uiSettings.isMapToolbarEnabled = true

        getAllDentistList()
        getAllClinicList()

        Log.i("", "Se llega hasta aquí")

    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(activityParent.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun enableLocation() {
        if(!::gmMap.isInitialized) return
        if(isLocationPermissionGranted()) {
            gmMap.isMyLocationEnabled = true
            gmMap.setOnMarkerClickListener(this)
        }else {
            requestLocationPermission()
            gmMap.setOnMarkerClickListener(this)
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activityParent,Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activityParent.applicationContext, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(activityParent, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                gmMap.isMyLocationEnabled = true
            }else {
                Toast.makeText(activityParent.applicationContext, "Para activar la localización ve a ajustes y acepte los permisos",
                Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }

      }

    override fun onResume() {
        super.onResume()
        if(!::gmMap.isInitialized) return
        if(!!isLocationPermissionGranted()) {
            gmMap.isMyLocationEnabled = false
            Toast.makeText(activityParent.applicationContext, "Para activar la localización ve a ajustes y acepte los permisos",
            Toast.LENGTH_SHORT).show()
        }

    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(activityParent.applicationContext, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(activityParent.applicationContext, "Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }



    private fun getAllDentistList() {
        mService
            .getAllDentistsList(
                limit = "30",
                offset = "0",
                name = "",
                latitude = latitude.toString(),
                longitude = longitude.toString())
            .enqueue(object: Callback<ApiResponse<Dentist>> {
            override fun onResponse(
                call: Call<ApiResponse<Dentist>>,
                response: Response<ApiResponse<Dentist>>
            ) {
                createMarkers(response.body()!!)
            }
            override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getAllClinicList() {
        mService.getAllClinicsList(limit = "30", offset = "0", name = "", latitude = latitude.toString(), longitude = longitude.toString()).enqueue(object: Callback<ApiResponse<Clinic>> {
            override fun onResponse(
                call: Call<ApiResponse<Clinic>>,
                response: Response<ApiResponse<Clinic>>
            ) {
                createMarkers1(response.body()!!)
            }
            override fun onFailure(call: Call<ApiResponse<Clinic>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createMarkers1(response : ApiResponse<Clinic>) {
        val list = response.data
        if(list.isNotEmpty()){
            list.map {
                val coordinates = LatLng(it.user!!.latitude!!.toDouble(), it.user!!.longitude!!.toDouble())
                val marker = MarkerOptions().position(coordinates).title(it.company_name)
                marker
                    .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                val marker_ : Marker? = gmMap.addMarker(marker)
                marker_!!.tag = it
            }
        }
    }
    private fun createMarkers(response : ApiResponse<Dentist>) {
        val list = response.data
        if(list.isNotEmpty()){
            list.map {
                val coordinates = LatLng(it.person!!.user!!.latitude!!.toDouble(), it.person!!.user!!.longitude!!.toDouble())
                val marker = MarkerOptions().position(coordinates).title(it.person!!.first_name + " " + it.person!!.last_name)
                marker
                    .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                val marker_ : Marker? = gmMap.addMarker(marker)
                marker_!!.tag = it
            }
        }
    }



   override fun onMarkerClick(p0: Marker): Boolean {
       try{
           val info : Dentist = p0.tag as Dentist
           val infoPerson : Person? = info.person
           val infoUser : User? = info.person!!.user
           binding.bottomsheet.txtNombre.text = infoPerson!!.first_name + " " + infoPerson!!.last_name
           binding.bottomsheet.txtDireccion.text = infoUser!!.direction
           binding.bottomsheet.txtRating.text = info.rating.toString()
           binding.bottomsheet.butMasInfo.setOnClickListener{
               val bundle : Bundle = Bundle()
               bundle.putString("id", info.id_dentist)
               bundle.putString("first_name", infoPerson.first_name)
               bundle.putString("first_name", infoPerson.first_name)
               bundle.putString("last_name", infoPerson.last_name)
               bundle.putString("direction", binding.bottomsheet.txtDireccion.text.toString())
               bundle.putString("rating", binding.bottomsheet.txtRating.text.toString())
               bundle.putString("gender", infoPerson.gender)
               bundle.putString("district", infoUser.direction)
               bundle.putString("dni", infoPerson.dni.toString())
               bundle.putString("phone_number", infoUser.phone_number.toString())
               bundle.putString("ruc", info.ruc)
               communicator.goToAnotherFragment(
                   bundle,
                   InfoDentistFragment(),
                   activityParent.containerView,
                   "MapFragment2InfoDentistFragment"
               )
           }
           if(binding.bottomsheet.root.visibility == View.VISIBLE) {
               binding.bottomsheet.root.visibility = View.GONE
           }
           else{
               binding.bottomsheet.root.visibility = View.VISIBLE
           }

       } catch (ex : Exception) {
           val info : Clinic = p0.tag as Clinic
           val infoUser : User? = info.user
           binding.bottomsheet.txtNombre.text = info.company_name
           binding.bottomsheet.txtDireccion.text = infoUser!!.direction
           binding.bottomsheet.txtRating.text = info.rating.toString()
           binding.bottomsheet.butMasInfo.setOnClickListener{
               val bundle : Bundle = Bundle()
               bundle.putString("id", info.id_clinic)
               bundle.putString("company_name",info.company_name )
               bundle.putString("direction", binding.bottomsheet.txtDireccion.text.toString())
               bundle.putString("rating", binding.bottomsheet.txtRating.text.toString())
               bundle.putString("phone_number", infoUser.phone_number.toString())
               bundle.putString("district", infoUser.district.toString())
               bundle.putString("ruc", info.ruc)

               communicator.goToAnotherFragment(
                   bundle,
                   InfoClinicFragment(),
                   activityParent.containerView,
                   "MapFragment2InfoDentistFragment"
               )
           }
           if(binding.bottomsheet.root.visibility == View.VISIBLE) {
               binding.bottomsheet.root.visibility = View.GONE
           }
           else{
               binding.bottomsheet.root.visibility = View.VISIBLE
           }
       }
       return false
    }

    override fun onMapClick(p0: LatLng) {
        binding.bottomsheet.root.visibility = View.GONE
    }

}