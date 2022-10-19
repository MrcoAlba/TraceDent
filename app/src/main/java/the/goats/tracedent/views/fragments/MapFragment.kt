package the.goats.tracedent.views.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import the.goats.tracedent.adapter.MyDentistAdapter
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentMapBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Test
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment


class MapFragment
    : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private lateinit var gmMap : GoogleMap
    private lateinit var mService : RetrofitService
    private lateinit var adapter : MyDentistAdapter
    private lateinit var bottomSheetFragment : View
    private lateinit var txtNombre : TextView
    private lateinit var txtDireccion : TextView
    private lateinit var txtRating : TextView
    private lateinit var butMasInfo : Button

    private var cantidadClicks = 0

    //private var lista : MutableList<List<Dentist>>? = null



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
       // bottomSheetFragment = BottomSheetFragment()

    }
    override fun onMapReady(googleMap: GoogleMap) {
        gmMap = googleMap
        //createMarker()
        gmMap.setOnMyLocationButtonClickListener(this)
        gmMap.setOnMyLocationClickListener(this)
        enableLocation()
        gmMap.setOnMapLongClickListener(this)

        gmMap.setOnMarkerClickListener(this)
        gmMap.uiSettings.isZoomControlsEnabled = true
        gmMap.uiSettings.isMapToolbarEnabled = true

        getAllDentistList()

    }
    private fun createMarker() {
        val coordinates = LatLng(-12.08546, -76.97122)
        val coordinates2 = LatLng(-11.0236652, -77.14551565)
        val lista : List<LatLng> = listOf(coordinates, coordinates2)
        val lista1 : List<String> = listOf("Ubicacion 1", "Ubicacion 2")
        var i = 0
        lista.map {
            val marker = MarkerOptions().position(it).title(lista1[i])
            val marker_: Marker? = gmMap.addMarker(marker)
            gmMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(it,18f),
                4000,
                null
            )

            marker_!!.tag = Test("FRANCO MARQUEZ", marker.title.toString(), "4.0")
            i = 1
        }
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(activityParent.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if(!::gmMap.isInitialized) return
        if(isLocationPermissionGranted()) {
            gmMap.isMyLocationEnabled = true
        }else {
            requestLocationPermission()
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

    private fun createMarkers(list : MutableList<Dentist>) {
        list.map {
            val coordinates = LatLng(-12.08546, -76.97122)
            val marker = MarkerOptions().position(coordinates).title(it.nombres + " " + it.apellidos)
            val marker_ : Marker? = gmMap.addMarker(marker)
            marker_!!.tag = it
        }
    }

    override fun onMapLongClick(p0: LatLng) {
        val markerOptions = MarkerOptions().position(p0)
        markerOptions.icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_VIOLET
            )
        )
        val nombreUbicacion = obtenerDireccion(p0)
        markerOptions.title(nombreUbicacion)


        val ultimoMarcador = gmMap.addMarker(markerOptions)
        gmMap.addMarker(markerOptions)
        gmMap.animateCamera(CameraUpdateFactory.newLatLng(p0))


        ultimoMarcador!!.tag = Test("Franco Marquez",nombreUbicacion,"4.0")
        cantidadClicks = 1

    }

    fun obtenerDireccion(latLng: LatLng) : String {
        val geocoder = Geocoder(activityParent.applicationContext)
        val direcciones : List<Address>?
        val primeraDireccion : Address
        var textoDireccion = ""

        try{
        direcciones = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if(direcciones != null && direcciones.isNotEmpty()) {
            primeraDireccion = direcciones[0]

            if(primeraDireccion.maxAddressLineIndex > 0) {
                for(i in 0 .. primeraDireccion.maxAddressLineIndex) {
                    textoDireccion += primeraDireccion.getAddressLine(i) + "\n"
                }
            }

            else {
                if(primeraDireccion.thoroughfare != null && primeraDireccion.subThoroughfare != null) {
                    textoDireccion += primeraDireccion.thoroughfare + ", " +
                            primeraDireccion.subThoroughfare + "\n"
                }
                else {
                    textoDireccion = "Direccion no encontrada"
                }

            }
        }

        } catch (e: Exception) {
            textoDireccion = "Direccion no encontrada"
        }

        return textoDireccion
    }

   override fun onMarkerClick(p0: Marker): Boolean {
       try{
           var clickCount : Integer =  p0.tag as Integer
       } catch (ex : Exception) {

           val info : Dentist = p0.tag as Dentist
           txtNombre.text = info.nombres + " " + info.apellidos
           txtDireccion.text = info.direccion
           txtRating.text = info.rating
           butMasInfo.setOnClickListener{
               val bundle : Bundle = Bundle()
               bundle.putString("nombres", info.nombres)
               bundle.putString("apellidos", info.apellidos)
               bundle.putString("direccion", txtDireccion.text.toString())
               bundle.putString("rating", txtRating.text.toString())
               bundle.putString("correo", info.correo)
               bundle.putString("distrito", info.distrito)

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
       }
       Log.i(null, p0.position.toString())
       return false
    }

    override fun onMapClick(p0: LatLng) {
        bottomSheetFragment.visibility = View.GONE
    }

}