package the.goats.tracedent.views.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    // A lambda to get the Activity"Name"Binding::inflate so the layout can be bound
    val bindingFactory : (LayoutInflater) -> VB
) : AppCompatActivity() {

    //ViewBinding of type VB -> Activity"Name"Binding
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    //Every activity should call this function in the onCreate to set it's first fragment
    fun transactionFirstAndMainFragment(fragment : Fragment, containerView : FragmentContainerView){
        supportFragmentManager.beginTransaction()
            .replace(containerView.id, fragment)
            .addToBackStack("main")
            .setReorderingAllowed(true)
            .commit()
    }

    fun transactionReplaceFragment(
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

    fun goToAnotherFragment(
        bundle: Bundle,
        fragment: Fragment,
        containerView: FragmentContainerView,
        transactionName: String
    ) {
        fragment.arguments = bundle
        transactionReplaceFragment(fragment, containerView, transactionName)
    }

}