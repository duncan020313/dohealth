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


class Fitnesslistfragment_tab6 : Fragment() {
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

        val cardiovascularexercise = listOf<String>("트레드 밀","싸이클","로잉 머신")
        datas.clear()
        for(i in cardiovascularexercise){
            val value = Fitnessitemdata(cardiovascularexercise.indexOf(i)+500, i, requireContext().resources.getDrawable(R.drawable.ic_launcher_background,requireContext().theme))
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