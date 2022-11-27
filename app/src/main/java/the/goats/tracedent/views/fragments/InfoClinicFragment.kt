package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentInfoClinicBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.appointment.AppointmentFragment

class InfoClinicFragment : BaseFragment<FragmentInfoClinicBinding>(FragmentInfoClinicBinding::inflate) {

    private lateinit var activityParent : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent  = requireActivity() as MainActivity

        fill()

        // Listeners
        binding.butReservarClinica.setOnClickListener      { makeAnAppointment()}
        binding.butRegresarClinica.setOnClickListener      { activityParent.onBackPressed() }
    }

    private fun makeAnAppointment() {
        val bundle = Bundle()
        bundle.putString("id", requireArguments().getString("id"))

        communicator.goToAnotherFragment(
            bundle,
            AppointmentFragment(),
            activityParent.containerView,
            "AppointmentFragment")
    }

    private fun fill() {
        binding.txtNombreClinica.text = requireArguments().getString("company_name")
        binding.txtTelefonoClinica.text = requireArguments().getString("phone_number")
        binding.txtDireccionClinica.text = requireArguments().getString("direction") + ", " + requireArguments().getString("district")
        binding.txtRatingClinica.text = requireArguments().getString("rating")
        binding.txtRucClinica.text = requireArguments().getString("ruc")
    }
}