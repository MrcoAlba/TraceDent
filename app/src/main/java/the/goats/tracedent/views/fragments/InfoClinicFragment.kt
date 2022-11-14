package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentInfoClinicBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class InfoClinicFragment : BaseFragment<FragmentInfoClinicBinding>(FragmentInfoClinicBinding::inflate) {

    private lateinit var activityParent : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity
        val bundle : Bundle = Bundle()
        bundle.putString("id", requireArguments().getString("id"))
        bundle.putString("company_name", requireArguments().getString("company_name"))
        bundle.putString("direction", requireArguments().getString("direction"))
        bundle.putString("rating", requireArguments().getString("rating"))
        bundle.putString("phone_number", requireArguments().getString("phone_number"))
        bundle.putString("district", requireArguments().getString("district"))
        bundle.putString("ruc", requireArguments().getString("ruc"))

        binding.butReservarClinica.setOnClickListener      {
            communicator.goToAnotherFragment(
            bundle,
            AppointmentFragment(),
            activityParent.containerView,
            "AppointmentFragment")
        }
        binding.butRegresarClinica.setOnClickListener      { activityParent.onBackPressed() }
        fill()
    }

    private fun fill() {
        binding.txtNombreClinica.text = requireArguments().getString("company_name")
        binding.txtTelefonoClinica.text = requireArguments().getString("phone_number")
        binding.txtDireccionClinica.text = requireArguments().getString("direction") + ", " + requireArguments().getString("district")
        binding.txtRatingClinica.text = requireArguments().getString("rating")
        binding.txtRucClinica.text = requireArguments().getString("ruc")
    }


}