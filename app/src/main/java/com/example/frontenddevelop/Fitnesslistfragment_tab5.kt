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
import androidx.recyclerview.widget.RecyclerView


class Fitnesslistfragment_tab5 : Fragment() {
    val datas = mutableListOf<Fitnessitemdata>()
    lateinit var recyclerView : RecyclerView
    lateinit var customadapter : CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fitnesslistfragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.fitnesslistfragment_recyclerview)
        initRecycler()
        return view
    }

    private fun initRecycler() {
        customadapter = CustomAdapter(requireContext())
        val arm = listOf<String>("바벨 컬","덤벨 컬","덤벨 삼두 익스텐션","덤벨 킥백","덤벨 리스트 컬","덤벨 해머 컬",
        "케이블 푸시 다운","클로즈그립 푸시업","이지바 컬","케이블 컬","케이블 삼두 익스텐션","시티드 덤벨 익스텐션",
        "스컬 크러셔","바벨 리스트 컬")
        datas.clear()
        for(i in arm){
            val value = Fitnessitemdata(arm.indexOf(i)+400, i, requireContext().resources.getDrawable(R.drawable.ic_launcher_background,requireContext().theme))
            datas.add(value)
        }
        customadapter.datas = datas
        recyclerView.adapter = customadapter
    }

    fun sendDatas(){
        var checkboxlist = customadapter.checkboxlist
        passData(checkboxlist as ArrayList<checkboxData>)
    }

    lateinit var dataPasser: OnDataPass

    fun passData(data: ArrayList<checkboxData>){
        dataPasser.onDataPass(data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }
}