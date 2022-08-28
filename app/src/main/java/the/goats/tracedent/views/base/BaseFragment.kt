package the.goats.tracedent.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import the.goats.tracedent.interfaces.Communicator

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if(_binding == null)
            throw IllegalArgumentException("Binding can not be null")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    To call the communicator, use the following:
        communicator.goToAnotherFragment(bundle)
    To get the bundle from the destination fragment, use the following:
        arguments?.getString(key: "message")
    */
}