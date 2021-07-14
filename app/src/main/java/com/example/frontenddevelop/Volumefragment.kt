package com.example.frontenddevelop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView


class Volumefragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_volumefragment, container, false)

        //Recyclerview 설정
        recyclerview = view.findViewById<RecyclerView>(R.id.volumefragment_recyclerview)
        groupadapter = addgroupadapter(requireContext())
        initRecycler()


        return view
    }

    fun initRecycler(){
        //여기서 db에서 데이터 받아오면 됩니다.
        val groupdatalist = mutableListOf<Groupdataclass>()
        val volumes = listOf("3대 500", "쫌 치는 분들 여기로")
        for (i in volumes){
            val value = Groupdataclass(i.hashCode(), i.toString(), "Hello","Hi",R.drawable.group)
            groupdatalist.add(value)
        }
        groupadapter.groupdatalist = groupdatalist
        recyclerview.adapter = groupadapter
        groupadapter.notifyDataSetChanged()
    }
}