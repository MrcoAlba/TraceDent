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
import the.goats.tracedent.api.OLDAPI.Clinic
import the.goats.tracedent.api.OLDAPI.Dentist
import the.goats.tracedent.api.OLDAPI.Person
import the.goats.tracedent.api.OLDAPI.Usuario
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentMapBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment


class MapFragment
    : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private lateinit var gmMap : GoogleMap
    private lateinit var mService : RetrofitService
    private lateinit var bottomSheetFragment : View
    private lateinit var txtNombre : TextView
    private lateinit var txtDireccion : TextView
    private lateinit var txtRating : TextView
    private lateinit var butMasInfo : Button


    companion object {
        const val REQUEST_CODE_LOCATION = 0
        val camera_zoom = 15
    }

    override fun onStart() {
        super.onStart()
        txtNombre = activityParent.findViewById(R.id.txtNombre)
        txtDireccion = activityParent.findViewById(R.id.txtDireccion)
        txtRating = activityParent.findViewById(R.id.txtRating)
        butMasInfo = activityParent.findViewById(R.id.butMasInfo)
        bottomSheetFragment = activityParent.findViewById(R.id.bottomsheet)

    }


    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "BaseFragment", "onViewCreated")


        mService = Common.retrofitService

        //Lifecycle necessary functions
        createFragment()

        txtNombre = activityParent.findViewById(R.id.txtNombre)
        txtDireccion = activityParent.findViewById(R.id.txtDireccion)
        txtRating = activityParent.findViewById(R.id.txtRating)
        butMasInfo = activityParent.findViewById(R.id.butMasInfo)
        bottomSheetFragment = activityParent.findViewById(R.id.bottomsheet)

        enableLocation()

    }

    private fun createFragment(){
        try {
            val supportMapFragment =
                childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
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
        mService.getAllDentistsList().enqueue(object: Callback<MutableList<Dentist>> {
            override fun onResponse(
                call: Call<MutableList<Dentist>>,
                response: Response<MutableList<Dentist>>
            ) {
                Log.i(null, "Se llego hasta aqui")
                Log.i(null, response.body().toString())
                createMarkers(response.body()!!)
            }

            override fun onFailure(call: Call<MutableList<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }

        })

    }

    private fun getAllClinicList() {
        mService.getAllClinicsList().enqueue(object: Callback<MutableList<Clinic>> {
            override fun onResponse(
                call: Call<MutableList<Clinic>>,
                response: Response<MutableList<Clinic>>
            ) {
                createMarkers1(response.body()!!)
            }

            override fun onFailure(call: Call<MutableList<Clinic>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }
        })
    }

    private fun createMarkers1(list : MutableList<Clinic>) {
        if(list.size>0){
            list.map {
                val coordinates = LatLng(it.user!!.latitude!!, it.user!!.longitude!!)
                val marker = MarkerOptions().position(coordinates).title(it.company_name)
                val marker_ : Marker? = gmMap.addMarker(marker)
                marker_!!.tag = it
                //Log.i(null, it.user!!.latitude.toString() + " " + it.user!!.longitude)
            }
        }
    }

    private fun createMarkers(list : MutableList<Dentist>) {
        list.map {
            val coordinates = LatLng(it.person!!.user!!.latitude!!, it.person!!.user!!.longitude!!)
            val marker = MarkerOptions().position(coordinates).title(it.person!!.first_name + " " + it.person!!.last_name)
            marker.icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_YELLOW
                )
            )
            val marker_ : Marker? = gmMap.addMarker(marker)
            marker_!!.tag = it
        }
    }



   override fun onMarkerClick(p0: Marker): Boolean {
       try{
           val info : Dentist = p0.tag as Dentist
           val infoPerson : Person? = info.person
           val infoUser : Usuario? = info.person!!.user
           txtNombre.text = infoPerson!!.first_name + " " + infoPerson!!.last_name
           txtDireccion.text = infoUser!!.direction
           txtRating.text = info.rating.toString()
           butMasInfo.setOnClickListener{
               val bundle : Bundle = Bundle()
               bundle.putString("id", info.id_dentist)
               bundle.putString("first_name", infoPerson.first_name)
               bundle.putString("first_name", infoPerson.first_name)
               bundle.putString("last_name", infoPerson.last_name)
               bundle.putString("direction", txtDireccion.text.toString())
               bundle.putString("rating", txtRating.text.toString())
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

           if(bottomSheetFragment.visibility == View.VISIBLE) {
               bottomSheetFragment.visibility = View.GONE
           }
           else{
               bottomSheetFragment.visibility = View.VISIBLE
           }

       } catch (ex : Exception) {

           val info : Clinic = p0.tag as Clinic
           val infoUser : Usuario? = info.user
           txtNombre.text = info.company_name
           txtDireccion.text = infoUser!!.direction
           txtRating.text = info.rating.toString()
           butMasInfo.setOnClickListener{
               val bundle : Bundle = Bundle()
               bundle.putString("id", info.id_clinic)
               bundle.putString("company_name",info.company_name )
               bundle.putString("direction", txtDireccion.text.toString())
               bundle.putString("rating", txtRating.text.toString())
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

           if(bottomSheetFragment.visibility == View.VISIBLE) {
               bottomSheetFragment.visibility = View.GONE
           }
           else{
               bottomSheetFragment.visibility = View.VISIBLE
           }
       }
       Log.i(null, p0.position.toString())
       return false
    }

    override fun onMapClick(p0: LatLng) {
        bottomSheetFragment.visibility = View.GONE
    }

}