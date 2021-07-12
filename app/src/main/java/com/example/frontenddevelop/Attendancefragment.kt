package com.example.frontenddevelop

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

class Attendancefragment : Fragment() {
    private lateinit var groupadapter : addgroupadapter
    private lateinit var recyclerview : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attendancefragment, container, false)

        //Recyclerview 설정
        recyclerview = view.findViewById<RecyclerView>(R.id.attendancefragment_recyclerview)
        groupadapter = addgroupadapter(requireContext())
        initRecycler()


        return view
    }

    fun initRecycler(){
        //여기서 db에서 데이터 받아오면 됩니다.
        val groupdatalist = mutableListOf<Groupdataclass>()
        for (i in 1..10){
            val value = Groupdataclass(i, i.toString(), "Hello","Hi",R.drawable.group)
            groupdatalist.add(value)
        }
        groupadapter.groupdatalist = groupdatalist
        recyclerview.adapter = groupadapter
        groupadapter.notifyDataSetChanged()
    }
}

class addgroupadapter(private val context: Context) : RecyclerView.Adapter<addgroupadapter.ViewHolder>() {
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
        private val groupintro : TextView = itemView.findViewById(R.id.groupitemlayout_introduce)

        fun bind(item: Groupdataclass, num:Int) {
            groupname.text = item.name
            groupinform.text = item.inform
            groupimage.setImageDrawable(context!!.resources.getDrawable(item.imageid,context!!.theme))
            groupintro.text = item.intro
            itemView.setOnClickListener{
                (context as Addgroupactivity).joingrouppopupstart(item)
            }
        }
    }
}