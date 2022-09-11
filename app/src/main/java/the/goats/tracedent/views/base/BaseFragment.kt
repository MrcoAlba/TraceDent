package the.goats.tracedent.views.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.analytics.FirebaseAnalytics
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import java.lang.Error

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!
    lateinit var analytics : FirebaseAnalytics
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
        try {
            val analytics = FirebaseAnalytics.getInstance(activity)
            val bundle = Bundle()
            bundle.putString("message", "Fragment: $message")
            analytics.logEvent(fragmentName, bundle)
        }catch (e: Error){
            Toast.makeText(
                activity,
                "There was an error with Firebase Analytics!!! ${e.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }

    }


}