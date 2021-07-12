package com.example.frontenddevelop

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

class Rankingfragment : Fragment() {
    private lateinit var groupadapter : rankingfragment_recyclerviewadapter
    private lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rankingfragment, container, false)

        //Recyclerview 설정
        recyclerview = view.findViewById<RecyclerView>(R.id.rankingfragment_recyclerview)
        groupadapter = rankingfragment_recyclerviewadapter(requireContext())
        initRecycler()


        return view
    }

    fun initRecycler(){
        val userdatalist = mutableListOf<Userdataclass>()
        for (i in 1..30){
            val value = Userdataclass(i, i.toString(), "User$i","Hi",R.drawable.user)
            userdatalist.add(value)
        }
        groupadapter.userdatalist = userdatalist
        recyclerview.adapter = groupadapter
        groupadapter.notifyDataSetChanged()
    }
}

class rankingfragment_recyclerviewadapter(private val context: Context) : RecyclerView.Adapter<rankingfragment_recyclerviewadapter.ViewHolder>() {
    var userdatalist = mutableListOf<Userdataclass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.useritemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userdatalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userdatalist[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val username: TextView = itemView.findViewById(R.id.useritemlayout_username)
        private val userinform: TextView = itemView.findViewById(R.id.useritemlayout_inform)
        private val userimage: ImageView = itemView.findViewById(R.id.useritemlayout_image)
        private  val userintro : TextView = itemView.findViewById(R.id.useritemlayout_introduce)

        fun bind(item: Userdataclass, num:Int) {
            username.text = item.name
            userinform.text = item.inform
            userimage.setImageDrawable(context!!.resources.getDrawable(item.profileid,context!!.theme))
            userintro.text = item.intro
            itemView.setOnClickListener{
                Log.e("userfrag",num.toString())
            }
        }
    }
}