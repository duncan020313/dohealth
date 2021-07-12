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


class Fitnesslistfragment_tab3 : Fragment() {
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

        val back = listOf<String>("풀업","바벨 로우","덤벨 로우","펜들레이 로우","시티드 로우 머신","렛풀 다운"
        ,"친업","백 익스텐션","시티드 케이블 로우","원암 덤벨 로우","중량 풀업","인클라인 바벨 로우","인버티드 로우",
        "바벨 풀오버","백 익스텐션","중량 하이퍼 익스텐션")
        datas.clear()
        for(i in back){
            val value = Fitnessitemdata(back.indexOf(i)+200, i, requireContext().resources.getDrawable(R.drawable.ic_launcher_background,requireContext().theme))
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