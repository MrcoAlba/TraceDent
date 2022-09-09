package the.goats.tracedent.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.goats.tracedent.views.model.Dentista

class MyDentistaAdapter(private val context:Context, private val dentistaList:MutableList<Dentista>): RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        LayoutInflater.from(parent.context)
        return MyViewHolder()
    }

    override fun getItemCount(): Int {
        return dentistaList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class ChatsBtwUsersHolder(view: View):RecyclerView.ViewHolder(view){
    val binding =ChatUsersBinding.bind(view)

    fun render (msg : MessageChat,userId : String){
        if(userId == msg.fromID){
            binding.textMessageSend.visibility = View.VISIBLE
            binding.textMessageReceived.visibility = View.GONE
            binding.textMessageSend.text = msg.content
        }else{
            binding.textMessageSend.visibility = View.GONE
            binding.textMessageReceived.visibility = View.VISIBLE
            binding.textMessageReceived.text = msg.content
        }

    }
}




