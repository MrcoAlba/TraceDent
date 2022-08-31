package the.goats.tracedent.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

interface Communicator {
    fun goToAnotherFragment(bundle: Bundle?,
                            fragment: Fragment,
                            containerView: FragmentContainerView,
                            transactionName: String)
    fun goToAnotherActivity(bundle: Bundle?,
                            activity: Activity)
}