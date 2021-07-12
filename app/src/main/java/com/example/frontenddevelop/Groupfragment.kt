package com.example.frontenddevelop

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView

val groupdatalist = mutableListOf<Groupdataclass>()
lateinit var groupadapter : groupfragment_recyclerviewadapter
class Groupfragment : Fragment() {
    private lateinit var recyclerview : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_groupfragment, container, false)

        //Recyclerview 설정
        recyclerview = view.findViewById<RecyclerView>(R.id.groupfragment_recyclerview)
        groupadapter = groupfragment_recyclerviewadapter(requireContext())
        initRecycler()
        return view
    }

    fun initRecycler(){
        //DB에서 데이터 가져오기
        if(groupdatalist.size==0){
            val value = Groupdataclass(-1, "Add", "","",requireContext().resources.getDrawable(R.drawable.plus_profile,requireContext().theme).toBitmap())
            groupdatalist.add(value)
        }
        groupadapter.groupdatalist = groupdatalist
        recyclerview.adapter = groupadapter
        groupadapter.notifyDataSetChanged()
    }
}

class groupfragment_recyclerviewadapter(private val context: Context) : RecyclerView.Adapter<groupfragment_recyclerviewadapter.ViewHolder>() {
    var groupdatalist = mutableListOf<Groupdataclass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.groupitemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupdatalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groupdatalist[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val groupname: TextView = itemView.findViewById(R.id.groupitemlayout_groupname)
        private val groupinform: TextView = itemView.findViewById(R.id.groupitemlayout_inform)
        private val groupimage: ImageView = itemView.findViewById(R.id.groupitemlayout_image)
        private val groupintro: TextView = itemView.findViewById(R.id.groupitemlayout_introduce)

        fun bind(item: Groupdataclass, num: Int) {
            groupname.text = item.name
            groupinform.text = item.inform
            groupimage.setImageBitmap(item.image)
            groupintro.text = item.intro
            itemView.setOnClickListener {
                if (num < groupdatalist.size - 1) {
                    (context as MainActivity).showgroupstart(item)
                } else {
                    (context as MainActivity).addgroupstart()
                }
            }
        }
    }
}