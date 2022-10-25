package the.goats.tracedent.views.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentAppointmentBinding
import the.goats.tracedent.databinding.FragmentAppointmentDBinding
import the.goats.tracedent.databinding.FragmentLoginBinding
import the.goats.tracedent.databinding.FragmentSearchBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.views.activities.LoginActivity
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import java.util.*


class AppointmentDFragment
    : BaseFragment<FragmentAppointmentDBinding>(FragmentAppointmentDBinding::inflate)
{
    //This variables are gonna be instantiated on the fragment lifecycle,
    //At the moment, they are null variables
    private lateinit var activityParent : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity

        //Firebase Analytics
        analyticEvent(requireActivity(), "AppointmentFragment", "onViewCreated")


        val especialidades= listOf("una","casa","bonita")
        val adapters=ArrayAdapter(activityParent, R.layout.list_item_especialidad,especialidades)
        //binding.autoCompleteEsp.adapter=adapters



        //darle logica con lo de X tmb
        //binding.btnDate.isClickable=true
        //binding.btnDate.isEnabled=true
        binding.btnDate.setOnClickListener{showDatePickerFragment()}


    }
    private fun showDatePickerFragment() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day,month,year)}
        datePicker.show(activityParent.supportFragmentManager,"datepicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){

    }
}