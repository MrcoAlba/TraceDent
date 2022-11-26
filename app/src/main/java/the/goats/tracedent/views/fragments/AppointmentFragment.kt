package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.databinding.FragmentAppointmentBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Recruitment
import the.goats.tracedent.common.Common
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment


class AppointmentFragment
    : BaseFragment<FragmentAppointmentBinding>(FragmentAppointmentBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private var dia : Int =-1
    private var mes : Int =-1
    private var año : Int =-1
    private var fecha: String = ""
    private lateinit var dentistaelegido: Dentist
    private lateinit var mService : RetrofitService
    private var filtro: String = "vacio"
    private val especialidades:MutableList<String> = mutableListOf()
    private val especialidadesFijas:List<String> = listOf("Cirugia","Diseño de sonrisa","Odontologia")
    private val dentistas:MutableList<String> = mutableListOf()
    private val horasdisponibles:MutableList<String> = mutableListOf()
    var horas: List<String> = listOf(
    /*"12:00am", "12:30am",
    "01:00am", "01:30am",
    "02:00am", "02:30am",
    "03:00am", "03:30am",
    "04:00am", "04:30am",
    "05:00am", "05:30am",
    "06:00am", "06:30am",
    "07:00am", "07:30am",*/
    "08:00am", "08:30am",
    "09:00am", "09:30am",
    "10:00am", "10:30am",
    "11:00am", "11:30am",
    "12:00am", "12:30am",
    "01:00pm", "01:30pm",
    "02:00pm", "02:30pm",
    "03:00pm", "03:30pm",
    "04:00pm", "04:30pm",
    "05:00pm", "05:30pm",
    /*"06:00pm", "06:30pm",
    "07:00pm", "07:30pm",
    "08:00pm", "08:30pm",
    "09:00pm", "09:30pm",
    "10:00pm", "10:30pm",
    "11:00pm", "11:30pm",
    "12:00pm", "12:30pm"*/
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity
        mService= Common.retrofitService
        //Firebase Analytics
        analyticEvent(requireActivity(), "AppointmentFragment", "onViewCreated")
        dentista()

        binding.btnDate.setOnClickListener{showDatePickerFragment()}
        binding.btnReservar.setOnClickListener {Toast.makeText(activityParent.baseContext,"Reserva exitosa",Toast.LENGTH_SHORT).show()}

    }
    private fun dentista() {

        val nombredentista:MutableList<String> = mutableListOf()
        nombredentista.add("NOMBRE02")
        nombredentista.add("NOMBRE02")
        val adapters = ArrayAdapter(
            activityParent.baseContext,
            android.R.layout.simple_spinner_dropdown_item,
            nombredentista
        )
        binding.autoCompleteTextView2.setAdapter(adapters)
        binding.autoCompleteTextView2.setOnItemClickListener { adapterView, view, i, l ->
            binding.autoCompleteTextView.isClickable=true
            binding.autoCompleteTextView.isEnabled=true
            especialidad2()
        }
        mService.getTheDentistsInAClinic("84fbc310-5639-11ed-916a-4bf9f16e0836").enqueue(object : Callback<MutableList<Recruitment>> {
            override fun onResponse(
                call: Call<MutableList<Recruitment>>,
                response: Response<MutableList<Recruitment>>
            ) {
                try {
                    val dentista: List<Recruitment> = response.body() as List<Recruitment>
                    val nombredentista:MutableList<String> = mutableListOf()
                    for (x:Int in 0 .. dentista.size){
                        nombredentista.add(dentista[x].dentist.person?.first_name.toString())
                    }
                    val adapters = ArrayAdapter(
                        activityParent.baseContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        nombredentista
                    )
                    binding.autoCompleteTextView2.setAdapter(adapters)
                    binding.autoCompleteTextView2.setOnItemClickListener { adapterView, view, i, l ->
                        binding.autoCompleteTextView.isClickable=true
                        binding.autoCompleteTextView.isEnabled=true
                        especialidad(dentista[i].dentist)
                    }
                }catch(e: Exception) {
                    println(response.body())
                    println(e.message)
                }


            }
            override fun onFailure(call: Call<MutableList<Recruitment>>, t: Throwable) {
                binding.autoCompleteTextView.isClickable=false
                binding.autoCompleteTextView.isEnabled=false
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!", t.message.toString())
            }
        })
    }
    private fun especialidad2() {
        val especialidad: MutableList<String> = mutableListOf("Odontologia", "Diseño de sonrisa","Cirugia")
        val adapters = ArrayAdapter(
            activityParent.baseContext,
            android.R.layout.simple_spinner_dropdown_item,
            especialidad
        )
        binding.autoCompleteTextView.setAdapter(adapters)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            binding.btnDate.isClickable = true
            binding.btnDate.isEnabled = true
        }
    }


    /*private fun dentista() {
        mService.getTheDentistsInAClinic("84fbc310-5639-11ed-916a-4bf9f16e0836").enqueue(object : Callback<MutableList<Recruitment>> {
            override fun onResponse(
                call: Call<MutableList<Recruitment>>,
                response: Response<MutableList<Recruitment>>
            ) {
                val dentista: MutableList<Recruitment> = response.body()!!
                val nombredentista:MutableList<String> = mutableListOf()
                for (x:Int in 0 .. dentista.size){
                    nombredentista.add(dentista[x].dentist.person?.first_name.toString())
                }
                val adapters = ArrayAdapter(
                    activityParent.baseContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    nombredentista
                )
                binding.autoCompleteTextView2.setAdapter(adapters)
                binding.autoCompleteTextView2.setOnItemClickListener { adapterView, view, i, l ->
                    binding.autoCompleteTextView.isClickable=true
                    binding.autoCompleteTextView.isEnabled=true
                    especialidad(dentista[i].dentist)
                }

            }
            override fun onFailure(call: Call<MutableList<Recruitment>>, t: Throwable) {
                binding.autoCompleteTextView.isClickable=false
                binding.autoCompleteTextView.isEnabled=false
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!", t.message.toString())
            }
        })
    }
*/
    private fun especialidad(dentista: Dentist) {
        val especialidad: MutableList<String> = mutableListOf("Odontologia","Diseño de sonrisa")
        val adapters = ArrayAdapter(
            activityParent.baseContext,
            android.R.layout.simple_spinner_dropdown_item,
            especialidad
        )
        binding.autoCompleteTextView.setAdapter(adapters)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            binding.btnDate.isClickable=true
            binding.btnDate.isEnabled=true
        }
        /*dentistaelegido= dentista
        mService.getTheEspecialidadInADentists(dentista.id_dentist!!).enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(
                call: Call<MutableList<String>>,
                response: Response<MutableList<String>>) {
                val especialidad: MutableList<String> = response.body()!!
                especialidad.add("Odontologia")
                especialidad.add("Diseño de sonrisa")
                val adapters = ArrayAdapter(
                    activityParent.baseContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    especialidad
                )
                binding.autoCompleteTextView.setAdapter(adapters)
                binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                    binding.btnDate.isClickable=true
                    binding.btnDate.isEnabled=true
                }
            }
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                binding.btnDate.isClickable=true
                binding.btnDate.isEnabled=true
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!", t.message.toString())
            }
        })*/
    }


    private fun showDatePickerFragment() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day,month,year)}
        datePicker.show(activityParent.supportFragmentManager,"datepicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){
        val month2:Int=month+1
        binding.txtfecha.text = "Día $day/$month2/$year"
        //send a get request for the date
        dia=day
        mes=month2
        año=year
        fecha="$day/$month2/$year"
        binding.txtfecha.visibility = View.VISIBLE

        /*mService.getTheScheduleOfAClinicDentistById(requireArguments().getString("id")!!,dentistaelegido.id_dentist!!,fecha).enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(
                call: Call<MutableList<String>>,
                response: Response<MutableList<String>>) {
                val captador: MutableList<String> = response.body()!!
                for (x:Int in 0 .. horas.size){
                    for (n:Int in 0 .. captador.size){
                        if (x.toString()==captador[n]){
                            horasdisponibles.add(horas[x])
                        }
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Log.e("gaaa!", t.message.toString())
            }
        })*/

        for (myString2 in horas/*horasdisponibles*/) {
            createchoiceChips(myString2)
        }
        choiceChips()
    }

    private fun createchoiceChips(name:String){
        val chip=Chip(context)
        chip.text=name
        val chipDrawable = ChipDrawable.createFromAttributes(
            activityParent,
            null,
            0,
            com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
        )
        chip.setChipDrawable(chipDrawable)
        binding.chipgroup.addView(chip)
    }

    private fun choiceChips(){
        binding.chipgroup
            .setOnCheckedChangeListener {
                    group, checkedId ->
                val chip: Chip?=group.findViewById(checkedId)
                if(chip?.isChecked==true){
                    binding.btnReservar.isClickable=true
                    binding.btnReservar.isEnabled=true
                    Toast.makeText(context,
                        "Selecciono la hora "+chip.text,
                        Toast.LENGTH_SHORT).show()
                    filtro= chip.text as String

                }else{
                    binding.btnReservar.isClickable=false
                    binding.btnReservar.isEnabled=false
                    Toast.makeText(context,
                        "Deselecciono la hora ",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}