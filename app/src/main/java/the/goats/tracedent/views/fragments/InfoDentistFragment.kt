package the.goats.tracedent.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import the.goats.tracedent.R
import the.goats.tracedent.databinding.FragmentInfoDentistBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class InfoDentistFragment : BaseFragment<FragmentInfoDentistBinding>(FragmentInfoDentistBinding::inflate) {

    private lateinit var activityParent : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator    =   requireActivity() as Communicator
        activityParent = requireActivity() as MainActivity

        binding.butReservarDentista.setOnClickListener      { }
        binding.butRegresarDentista.setOnClickListener      { activityParent.onBackPressed() }
        fill()
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