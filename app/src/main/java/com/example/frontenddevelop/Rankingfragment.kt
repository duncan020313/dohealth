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
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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
            val value = Userdataclass(i, i.toString(), "User$i","Hi",requireContext().resources.getDrawable(R.drawable.ic_launcher_background,requireContext().theme).toBitmap())
            userdatalist.add(value)
        }
        groupadapter.userdatalist = userdatalist
        recyclerview.adapter = groupadapter
        groupadapter.notifyDataSetChanged()
    }
}

class rankingfragment_recyclerviewadapter(private val context: Context) : RecyclerView.Adapter<rankingfragment_recyclerviewadapter.ViewHolder>() {
    var userdatalist = mutableListOf<Userdataclass>()
    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrogitInterface


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.useritemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userdatalist.size
    }

    fun getRank(){
        lateinit var UsersRank: ArrayList<String>
        supplementService.reponseRank("7").enqueue(object: Callback<ArrayList<String>> {
            override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>){
                if(response.code() == 200) {
                    UsersRank = response.body()!!
                    Log.d("Tag", UsersRank[0])
                }
            }
            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Log.d("TAG", t.toString())
            }
        })

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userdatalist[position], position)
    }

    private fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrogitInterface::class.java)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val username: TextView = itemView.findViewById(R.id.useritemlayout_username)
        private val userinform: TextView = itemView.findViewById(R.id.useritemlayout_inform)
        private val userimage: ImageView = itemView.findViewById(R.id.useritemlayout_image)
        private  val userintro : TextView = itemView.findViewById(R.id.useritemlayout_introduce)

        fun bind(item: Userdataclass, num:Int) {
            username.text = item.name
            userinform.text = item.inform
            userimage.setImageBitmap(item.profile)
            userintro.text = item.intro
            itemView.setOnClickListener{
                Log.e("userfrag",num.toString())
            }
        }
    }
}