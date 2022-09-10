package the.goats.tracedent.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import the.goats.tracedent.adapter.MyDentistAdapter
import the.goats.tracedent.common.Common
import the.goats.tracedent.databinding.FragmentSearchBinding
import the.goats.tracedent.interfaces.Communicator
import the.goats.tracedent.interfaces.Credential
import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.views.activities.MainActivity
import the.goats.tracedent.views.base.BaseFragment

class SearchFragment
    : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    lateinit var mService : RetrofitService
    lateinit var layoutManager : LinearLayoutManager
    lateinit var activityParent : MainActivity
    lateinit var adapter : MyDentistAdapter

    //Fragment Lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delegates
        communicator    =   requireActivity() as Communicator
        logout          =   requireActivity() as Credential.LogOut
        activityParent  =   requireActivity() as MainActivity
        //Firebase Analytics
        analyticEvent(requireActivity(), "SearchFragment", "onViewCreated")


        //Listeners
        binding.svSearcher.setOnQueryTextListener(this)
        //search("")

        //recycler view
        mService = Common.retrofitService
        binding.rvListadodata.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvListadodata.layoutManager = layoutManager
        getAllDentistList()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        getTheDentistList(query?:"")
        binding.svSearcher.clearFocus()
        return true
    }

    private fun getAllDentistList() {
        mService.getAllDentistsList().enqueue(object: Callback<MutableList<Dentist>> {
            override fun onResponse(
                call: Call<MutableList<Dentist>>,
                response: Response<MutableList<Dentist>>
            ) {
                adapter = MyDentistAdapter(requireContext(), response.body() as List<Dentist>, {})
                adapter.notifyDataSetChanged()
                binding.rvListadodata.adapter = adapter


            }

            override fun onFailure(call: Call<MutableList<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }


        })
    }

    private fun getTheDentistList(query: String) {
        mService.getDentistsList(query).enqueue(object: Callback<MutableList<Dentist>> {
            override fun onResponse(
                call: Call<MutableList<Dentist>>,
                response: Response<MutableList<Dentist>>
            ) {
                adapter = MyDentistAdapter(requireContext(), response.body() as List<Dentist>, {})
                adapter.notifyDataSetChanged()
                binding.rvListadodata.adapter = adapter


            }

            override fun onFailure(call: Call<MutableList<Dentist>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("gaaa!",t.message.toString())
            }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        getTheDentistList(query?:"")
        return true
    }

    override fun onClose(): Boolean {
        getAllDentistList()
        binding.svSearcher.clearFocus()
        return true
    }

}