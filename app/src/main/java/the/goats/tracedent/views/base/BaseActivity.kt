package the.goats.tracedent.views.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.viewbinding.ViewBinding
import the.goats.tracedent.interfaces.Communicator

abstract class BaseActivity<VB : ViewBinding>(
    // A lambda to get the Activity"Name"Binding::inflate so the layout can be bound
    val bindingFactory : (LayoutInflater) -> VB
) : AppCompatActivity(), Communicator {

    //ViewBinding of type VB -> Activity"Name"Binding
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
    }

    // If you go from activityA to activityB, activityA is gonna be destroyed
    override fun onStop() {
        super.onStop()
        finish()
    }

    //Every activity should call this function in the onCreate to set it's first fragment
    fun transactionFirstAndMainFragment(fragment : Fragment, containerView : FragmentContainerView){
        supportFragmentManager.beginTransaction()
            .replace(containerView.id, fragment)
            .addToBackStack("main")
            .setReorderingAllowed(true)
            .commit()
    }

    private fun transactionReplaceFragment(
        fragment : Fragment,
        containerView : FragmentContainerView,
        transactionName : String
    ){
        supportFragmentManager.beginTransaction()
            .replace(containerView.id, fragment)
            .addToBackStack(transactionName)
            .setReorderingAllowed(true)
            .commit()
    }

    // Use to go from fragmentA to new fragmentB
    override fun goToAnotherFragment(
        bundle: Bundle?,
        fragment: Fragment,
        containerView: FragmentContainerView,
        transactionName: String
    ) {
        fragment.arguments = bundle
        transactionReplaceFragment(fragment, containerView, transactionName)
    }

}