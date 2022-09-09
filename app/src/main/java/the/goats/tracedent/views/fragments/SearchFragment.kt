package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.View
import the.goats.tracedent.databinding.FragmentSearchBinding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Query
import the.goats.tracedent.R
import the.goats.tracedent.views.base.BaseFragment


class SearchFragment  : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate){
    //private lateinit var adapter: DataAdapter
    //private val dataDentistas= mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Here should be coded the logic

        //Code example to go from this view to another
        //communicator = view as MainActivity
        //communicator.goToAnotherFragment(bundle, fragment, containerView, transactionName)
        binding.rvListadodata

    //initRecyclerView()


    }

//    private fun initRecyclerView() {
//        adapter= DataAdapter(context,dataDentistas)
//        binding.rvListadodata.layoutManager=LinearLayoutManager(this)
//    }
//
//    fun getRetrofit():Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("tracedent-api.herokuapp.com/api/")
//            .build()
//    }
//    fun searchByName(query: String){
//        CoroutineScope(Dispatchers.IO).launch {
//            val call = getRetrofit().create(APIService::class.java).getCharacterByName("$query")
//            val dentistas = call.body()
//            if (call.isSuccessful){
//                //show RecyclerView
//            }else{
//                //show error
//            }
//        }
//    }
}