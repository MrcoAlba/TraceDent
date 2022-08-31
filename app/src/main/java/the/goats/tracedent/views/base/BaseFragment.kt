package the.goats.tracedent.views.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.analytics.FirebaseAnalytics
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!

    lateinit var communicator: Communicator
    lateinit var login: Credential.LogIn
    lateinit var logout: Credential.LogOut

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

    fun analyticEvent(activity: Activity, fragmentName: String, message: String){
        val analytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        val bundle = Bundle()
        bundle.putString("message", message)
        analytics.logEvent(fragmentName, bundle)
        Log.e("1","123")
    }
}