package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentInfoDentistBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment
import the.goats.tracedent.views.fragments.appointment.AppointmentDFragment

class InfoDentistFragment : BaseFragment<FragmentInfoDentistBinding>(FragmentInfoDentistBinding::inflate) {

    private lateinit var activityParent : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity

        fill()

        // Listeners
        binding.butReservarDentista.setOnClickListener      { makeAnAppointment() }
        binding.butRegresarDentista.setOnClickListener      { activityParent.onBackPressed() }
    }

    private fun makeAnAppointment() {
        val bundle = Bundle()
        bundle.putString("id", requireArguments().getString("id"))

        communicator.goToAnotherFragment(
            bundle,
            AppointmentDFragment(),
            activityParent.containerView,
            "AppointmentFragment")
    }

    private fun fill() {
        binding.txtNombreDentista.text = requireArguments().getString("first_name") + " " + requireArguments().getString("last_name")
        binding.txtTelefonoDentista.text = requireArguments().getString("phone_number")
        binding.txtDireccionDentista.text = requireArguments().getString("direction") + ", " + requireArguments().getString("district")
        binding.txtRatingDentista.text = requireArguments().getString("rating")
        binding.txtGeneroDentista.text = requireArguments().getString("gender")
        binding.txtDNIDentista.text = requireArguments().getString("dni")
        binding.txtRUCDentista.text = requireArguments().getString("ruc")
    }


}