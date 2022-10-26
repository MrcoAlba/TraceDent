package the.goats.tracedent.views.fragments

/*import android.R*/
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentAppointmentBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import java.util.*


class AppointmentFragment
    : BaseFragment<FragmentAppointmentBinding>(FragmentAppointmentBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity
    private var dia : Int =-1
    private var mes : Int =-1
    private var año : Int =-1
    private var filtro: String = "vacio"
    private val especialidades:MutableList<String> = mutableListOf()
    private val especialidadesFijas:List<String> = listOf("Cirugia","Diseño de sonrisa","Odontologia")
    private val dentistas:MutableList<String> = mutableListOf()
    private val horasdisponibles:MutableList<String> = mutableListOf()
    private var horas:List<String> = listOf("12:00am","12:30am",
                                            "01:00am","01:30am",
                                            "02:00am","02:30am",
                                            "03:00am","03:30am",
                                            "04:00am","04:30am",
                                            "05:00am","05:30am",
                                            "06:00am","06:30am",
                                            "07:00am","07:30am",
                                            "08:00am","08:30am",
                                            "09:00am","09:30am",
                                            "10:00am","10:30am",
                                            "11:00am","11:30am",
                                            "12:00am","12:30am",
                                            "01:00pm","01:30pm",
                                            "02:00pm","02:30pm",
                                            "03:00pm","03:30pm",
                                            "04:00pm","04:30pm",
                                            "05:00pm","05:30pm",
                                            "06:00pm","06:30pm",
                                            "07:00pm","07:30pm",
                                            "08:00pm","08:30pm",
                                            "09:00pm","09:30pm",
                                            "10:00pm","10:30pm",
                                            "11:00pm","11:30pm",
                                            "12:00pm","12:30pm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "AppointmentFragment", "onViewCreated")
        especialidad()
        dentista()


        //darle logica con lo de X tmb
        //binding.btnDate.isClickable=true
        //binding.btnDate.isEnabled=true
        binding.btnDate.setOnClickListener{showDatePickerFragment()}

    }

    private fun especialidad() {
        especialidades.add("Odontologia")
        especialidades.add("Cirugia")
        especialidades.add("Diseño de sonrisa")
        /*for (x:Int in 0 .. especialidadesFijas.size){
            //condicion de get
            if (especialidadesFijas[x]=="aa"){
                especialidades.add(horas[x])
            }
        }*/

        val adapters=ArrayAdapter(activityParent.baseContext, android.R.layout.simple_spinner_dropdown_item,especialidades)
        binding.autoCompleteTextView.setAdapter(adapters)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
        Toast.makeText(context,
            adapterView.getItemAtPosition(i).toString(),
            Toast.LENGTH_SHORT).show()
        }
    }
    private fun dentista() {
        dentistas.add("Franco")
        dentistas.add("Marco")
        dentistas.add("Matias")
        /*for (x:Int in 0 .. dentistas.size){
            dentistas.add(horas[x])
        }*/

        val adapters=ArrayAdapter(activityParent.baseContext, android.R.layout.simple_spinner_dropdown_item,dentistas)
        binding.autoCompleteTextView2.setAdapter(adapters)
        binding.autoCompleteTextView2.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(context,
                adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT).show()
        }
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
        binding.txtfecha.visibility = View.VISIBLE
        //request get para inflar el horasdisponibles
        /*for (x:Int in 0 .. horas.size){
            //condicion de get
            if (horas[x]=="aa"){
                horasdisponibles.add(horas[x])
            }
        }*/

        //deberia inflarse con lo de horasdisponibles, CAMBIAR LUEGO
        for (myString2 in horas/*debe ser horasdispinibles*/){
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
                    Toast.makeText(context,
                        "Filtro "+chip.text,
                        Toast.LENGTH_SHORT).show()
                    filtro= chip.text as String
                }else{
                    filtro="vacio"
                    Toast.makeText(context,
                        "Filtro "+filtro,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}