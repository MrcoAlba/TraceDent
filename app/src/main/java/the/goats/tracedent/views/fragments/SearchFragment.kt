package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentSearchBinding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import the.goats.tracedent.R
import the.goats.tracedent.views.base.BaseFragment


    class SearchFragment  : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Here should be coded the logic

        //Code example to go from this view to another
        //communicator = view as MainActivity
        //communicator.goToAnotherFragment(bundle, fragment, containerView, transactionName)
    }
}