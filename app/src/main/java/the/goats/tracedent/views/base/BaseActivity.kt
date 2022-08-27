package the.goats.tracedent.views.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
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

}