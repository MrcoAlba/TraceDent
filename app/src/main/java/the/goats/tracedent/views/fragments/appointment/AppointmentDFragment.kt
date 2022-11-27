package the.goats.tracedent.views.fragments.appointment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.R
import the.goats.tracedent.api.ApiResponse
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentAppointmentDBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.model.DentistSpecialities
import the.goats.tracedent.model.Schedule
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.DatePickerFragment

class AppointmentDFragment
    : BaseFragment<FragmentAppointmentDBinding>(FragmentAppointmentDBinding::inflate) {
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent: MainActivity
    private var dia: Int = -1
    private var mes: Int = -1
    private var año: Int = -1
    private var fecha: String = ""

    private lateinit var selectedSpeciality: DentistSpecialities
    private lateinit var availableSchedules: List<Schedule>
    private lateinit var selectedSchedule: Schedule

    private var filtro: String = "vacio"

    private var horas: List<String> = listOf(
        "12:00am", "12:30am",
        "01:00am", "01:30am",
        "02:00am", "02:30am",
        "03:00am", "03:30am",
        "04:00am", "04:30am",
        "05:00am", "05:30am",
        "06:00am", "06:30am",
        "07:00am", "07:30am",
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
        "06:00pm", "06:30pm",
        "07:00pm", "07:30pm",
        "08:00pm", "08:30pm",
        "09:00pm", "09:30pm",
        "10:00pm", "10:30pm",
        "11:00pm", "11:30pm",
        "12:00pm", "12:30pm"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator = requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        // Retrofit
        mService = Common.retrofitService

        // Dropdown list logic
        getAllSpecialities()

        binding.btnDate.setOnClickListener          { showDatePickerFragment() }
        binding.btnReservar.setOnClickListener      { selectSchedule() }
    }

    private fun getAllSpecialities() {
        mService.getDentistByIdAllSpecialities(requireArguments().getString("id")!!, "100", "0","")
            .enqueue(object : Callback<ApiResponse<DentistSpecialities>>{
                override fun onResponse(
                    call: Call<ApiResponse<DentistSpecialities>>,
                    response: Response<ApiResponse<DentistSpecialities>>
                ) {
                    val specialities = response.body()!!.data
                    val specialityNames = mutableListOf<String>()
                    for (index in specialities.indices){
                        specialities[index].speciality.name?.let { specialityNames.add(it) }
                    }
                    val adapters = ArrayAdapter(
                        activityParent.baseContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        specialityNames
                    )
                    binding.autoCompleteTextView.setAdapter(adapters)
                    binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                        binding.btnDate.isClickable=true
                        binding.btnDate.isEnabled=true
                        selectedSpeciality = specialities[i]
                    }
                }
                override fun onFailure(call: Call<ApiResponse<DentistSpecialities>>, t: Throwable) {
                    binding.btnDate.isClickable=false
                    binding.btnDate.isEnabled=false
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun showDatePickerFragment() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day,month,year)}
        datePicker.show(activityParent.supportFragmentManager,"datepicker")
    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {

        binding.txtfecha.text = "Día $day/$month/$year"

        val parsedDate: String? =
            if(month<10){
                if(day<10){
                    "${year}-0${month + 1}-0${day}"
                }else{
                    "${year}-0${month + 1}-${day}"
                }
            }else{
                if(day<10){
                    "${year}-${month + 1}-0${day}"
                }else{
                    "${year}-${month + 1}-${day}"
                }
            }


        /*FORMATO: 2022-11-25T00:00:05.007Z*/
        fecha=parsedDate!!+"T00:00:05.007Z"
        println(fecha)
        // Todo: Revisar esta parte xd
        binding.txtfecha.visibility = View.VISIBLE
        mService
            .getAllScheduleByDentistAndTime("0","100",requireArguments().getString("id")!!,fecha)
            .enqueue(object : Callback<ApiResponse<Schedule>> {
                override fun onResponse(
                    call: Call<ApiResponse<Schedule>>,
                    response: Response<ApiResponse<Schedule>>
                ) {

                    val schedules = response.body()!!.data
                    availableSchedules = schedules
                    val scheduleTimes = mutableListOf<String>()
                    for (index in schedules.indices){
                        scheduleTimes.add(horas[schedules[index].time!!])
                    }
                    for (time in scheduleTimes) {
                        createChoiceChips(time)
                    }
                    choiceChips()
                }
                override fun onFailure(call: Call<ApiResponse<Schedule>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun createChoiceChips(name:String){
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
                    for (index in availableSchedules.indices){
                        if (horas[availableSchedules[index].time!!] == chip.text){
                            selectedSchedule = availableSchedules[index]
                        }
                    }
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
    private fun selectSchedule() {
        val prefs = activityParent.getSharedPreferences(getString(R.string.sp_shared_preferences),0)
        val idPatient : String? = prefs.getString(getString(R.string.sp_patient_id),null)

        mService
            .s3patientChoosesSchedule(
                Schedule(
                    selectedSchedule.id_schedule, null,null,
                    null, idPatient,null,
                    null,null,null,null
                ))
            .enqueue(object: Callback<ApiResponse<Int>>{
                override fun onResponse(
                    call: Call<ApiResponse<Int>>,
                    response: Response<ApiResponse<Int>>
                ) {
                    if(response.body()!!.data[0] == 1){
                        Toast
                            .makeText(
                                activityParent,
                                "La reserva fue exitosa!",
                                Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        Toast
                            .makeText(
                                activityParent,
                                "Seleccione otra fecha por favor",
                                Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Int>>, t: Throwable) {
                    Toast
                        .makeText(
                            activityParent,
                            "No se pudo realizar la reserva, intente con otro horario por favor",
                            Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}