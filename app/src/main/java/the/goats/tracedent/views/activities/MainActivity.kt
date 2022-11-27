package the.goats.tracedent.views.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Callback
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.ActivityMainBinding
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.Patient
import the.goats.tracedent.views.base.BaseActivity
import the.goats.tracedent.views.fragments.MapFragment
import the.goats.tracedent.views.fragments.SearchFragment
import the.goats.tracedent.views.fragments.UsuarioFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), Credential.LogOut {

    private val principalFragments : List<Fragment> = listOf(
        SearchFragment(),
        MapFragment(),
        UsuarioFragment()
    )
    var back = false

    fun createDialog(message: String, cancelButton: String, acceptButton: String,doOnCancel: ()->Unit,doOnAccept: ()-> Unit): AlertDialog? {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_confirm_accept_invitation,null)

        val textView = view.findViewById<TextView>(R.id.tvMessage)
        val btnCancelar = view.findViewById<Button>(R.id.btn_cancelar)
        val btnAceptar = view.findViewById<Button>(R.id.btn_aceptar)

        textView.text = message
        btnCancelar.text = cancelButton
        btnAceptar.text = acceptButton

        btnCancelar.setOnClickListener { doOnCancel()}
        btnAceptar.setOnClickListener { doOnAccept() }

        builder.setView(view)


        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionFirstAndMainFragment(MapFragment(), binding.fcvMainActivity)
        containerView = binding.fcvMainActivity
        // Retrofit
        mService        =   Common.retrofitService
        updateUserData()
        // NAVIGATION BAR
        binding.bottomNavigationView.setOnItemSelectedListener { selectNavigationOption(it) }
        binding.bottomNavigationView.selectedItemId = R.id.map_item
    }

    /**
     * Moves from one navigation option to another
     */
    private fun selectNavigationOption(menuItem: MenuItem) : Boolean{
        val ft = supportFragmentManager.beginTransaction()
        when (menuItem.itemId) {
            //Here goes the transitions between fragments, each lines performs when an item is pressed
            R.id.booking_item   -> Toast .makeText(applicationContext, "Se presionó para pasar a las reservas", Toast.LENGTH_SHORT).show()
            R.id.search_item    -> ft.replace(binding.fcvMainActivity.id, principalFragments[0])
            R.id.map_item       -> ft.replace(binding.fcvMainActivity.id, principalFragments[1])
            R.id.profile_item   -> ft.replace(binding.fcvMainActivity.id, principalFragments[2])
            R.id.messaging_item -> Toast.makeText(applicationContext, "Se presionó para pasar a los chats", Toast.LENGTH_SHORT).show()
            else                -> Toast.makeText(applicationContext, "Se presionó una opción incorrecta", Toast.LENGTH_SHORT).show()
        }
        ft.addToBackStack(null)
        ft.commit()
        return true
    }
    /**
     * Moves from the MainActivity to the LoginActivity
     */
    override fun Main2Login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    /**
     * If back variable is true, then it go back
     */
    override fun onBackPressed() {
        if (!back){
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext, "No puede retroceder", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserData(){
        val prefs = this.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        when(prefs.getString(getString(R.string.sp_user_type),null)){
            "patient"   -> updatePatientData(prefs.getString(getString(R.string.sp_patient_id),null)!!)
            "dentist"   -> updateDentistData(prefs.getString(getString(R.string.sp_dentist_id),null)!!)
            else        -> updateClinicData (prefs.getString(getString(R.string.sp_clinic_id),null)!!)
        }
    }
    private fun updatePatientData(idPatient: String){
        mService.getPatientById(idPatient)
            .enqueue(object : Callback<ApiResponse<Patient>> {
                override fun onResponse(
                    call: Call<ApiResponse<Patient>>,
                    response: Response<ApiResponse<Patient>>
                ) {
                    val patient = response.body()?.data?.get(0) as Patient
                    with(this@MainActivity.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                        .edit()){
                        putString   (getString(R.string.sp_patient_id),     patient.id_patient                      )
                        putString   (getString(R.string.sp_person_id),      patient.person!!.id_person              )
                        putString   (getString(R.string.sp_first_name),     patient.person!!.first_name             )
                        putString   (getString(R.string.sp_last_name),      patient.person!!.last_name              )
                        putString   (getString(R.string.sp_gender),         patient.person!!.gender                 )
                        putLong     (getString(R.string.sp_dni),            patient.person!!.dni!!                  )
                        putString   (getString(R.string.sp_user_id),        patient.person!!.user!!.id_user         )
                        putString   (getString(R.string.sp_user_type),      patient.person!!.user!!.user_type       )
                        putLong     (getString(R.string.sp_phone_number),   patient.person!!.user!!.phone_number!!  )
                        putBoolean  (getString(R.string.sp_subscription),   patient.person!!.user!!.subscription!!  )
                        putString   (getString(R.string.sp_district),       patient.person!!.user!!.district        )
                        putString   (getString(R.string.sp_direction),      patient.person!!.user!!.direction       )
                        putFloat    (getString(R.string.sp_latitude),       patient.person!!.user!!.latitude!!  )
                        putFloat    (getString(R.string.sp_longitude),      patient.person!!.user!!.longitude!!  )
                    }.commit()
                }
                override fun onFailure(call: Call<ApiResponse<Patient>>, t: Throwable) {
                    Toast .makeText(applicationContext, "No se pudo encontrar la data del paciente", Toast.LENGTH_SHORT).show()
                    Main2Login()
                }

            })
    }
    private fun updateDentistData(idDentist: String){
        mService.getDentistById(idDentist)
            .enqueue(object : Callback<ApiResponse<Dentist>> {
                override fun onResponse(
                    call: Call<ApiResponse<Dentist>>,
                    response: Response<ApiResponse<Dentist>>
                ) {
                    val dentist = response.body()?.data?.get(0) as Dentist
                    with(this@MainActivity.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                        .edit()){
                        putString   (getString(R.string.sp_patient_id),     dentist.id_dentist                      )
                        putString   (getString(R.string.sp_dentist_ruc),    dentist.ruc                             )
                        putFloat    (getString(R.string.sp_dentist_rating), dentist.rating!!                        )
                        putString   (getString(R.string.sp_person_id),      dentist.person!!.id_person              )
                        putString   (getString(R.string.sp_first_name),     dentist.person!!.first_name             )
                        putString   (getString(R.string.sp_last_name),      dentist.person!!.last_name              )
                        putString   (getString(R.string.sp_gender),         dentist.person!!.gender                 )
                        putLong     (getString(R.string.sp_dni),            dentist.person!!.dni!!                  )
                        putString   (getString(R.string.sp_user_id),        dentist.person!!.user!!.id_user         )
                        putString   (getString(R.string.sp_user_type),      dentist.person!!.user!!.user_type       )
                        putLong     (getString(R.string.sp_phone_number),   dentist.person!!.user!!.phone_number!!  )
                        putBoolean  (getString(R.string.sp_subscription),   dentist.person!!.user!!.subscription!!  )
                        putString   (getString(R.string.sp_district),       dentist.person!!.user!!.district        )
                        putString   (getString(R.string.sp_direction),      dentist.person!!.user!!.direction       )
                        putFloat    (getString(R.string.sp_latitude),       dentist.person!!.user!!.latitude!!)
                        putFloat    (getString(R.string.sp_longitude),      dentist.person!!.user!!.longitude!!  )
                    }.commit()
                }
                override fun onFailure(call: Call<ApiResponse<Dentist>>, t: Throwable) {
                    Toast .makeText(applicationContext, "No se pudo encontrar la data del dentista", Toast.LENGTH_SHORT).show()
                    Main2Login()
                }

            })
    }
    private fun updateClinicData(idClinic: String){
        mService.getClinicById(idClinic)
            .enqueue(object : Callback<ApiResponse<Clinic>> {
                override fun onResponse(
                    call: Call<ApiResponse<Clinic>>,
                    response: Response<ApiResponse<Clinic>>
                ) {
                    try {
                        val clinics = response.body() as ApiResponse<Clinic>
                        val clinic = clinics.data[0]

                        with(this@MainActivity.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                            .edit()){
                            putString   (getString(R.string.sp_clinic_id),      clinic.id_clinic              )
                            putString   (getString(R.string.sp_clinic_ruc),     clinic.ruc                    )
                            putString   (getString(R.string.sp_clinic_company_name),clinic.company_name       )
                            putFloat    (getString(R.string.sp_clinic_rating),  clinic.rating!!               )
                            putString   (getString(R.string.sp_user_id),        clinic.user!!.id_user         )
                            putString   (getString(R.string.sp_user_type),      clinic.user!!.user_type       )
                            putLong     (getString(R.string.sp_phone_number),   clinic.user!!.phone_number!!  )
                            putBoolean  (getString(R.string.sp_subscription),   clinic.user!!.subscription!!  )
                            putString   (getString(R.string.sp_district),       clinic.user!!.district        )
                            putString   (getString(R.string.sp_direction),      clinic.user!!.direction       )
                            putFloat    (getString(R.string.sp_latitude),       clinic.user!!.latitude!!     )
                            putFloat    (getString(R.string.sp_longitude),      clinic.user!!.longitude!!    )
                        }.commit()
                    }catch (e:Exception){
                        println(response)
                        println(e.message)
                    }

                }
                override fun onFailure(call: Call<ApiResponse<Clinic>>, t: Throwable) {
                    Toast .makeText(applicationContext, "No se pudo encontrar la data de la clinica", Toast.LENGTH_SHORT).show()
                    println(t.message)
                    println(t)
                    val prefs = getSharedPreferences(getString(R.string.sp_shared_preferences),0)
                    if (prefs.edit().clear().commit()){
                        Main2Login()
                    }
                }

            })
    }
}