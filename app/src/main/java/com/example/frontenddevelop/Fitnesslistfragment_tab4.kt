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


class Fitnesslistfragment_tab4 : Fragment() {
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

        val shoulder = listOf<String>("오버헤드 프레스","덤벨 숄더 프레스","덤벨 레터럴 레이즈","덤벨 프론트 레이즈",
            "덤벨 슈러그","비하인드 넥 프레스","페이스 풀","핸드스탠드 푸시업","케이블 리버스 플라이",
            "바벨 업라이트 로우","벤트오버 덤벨 레터럴 레이즈","아놀드 덤벨 프레스",
        "숄더 프레스 머신","이지바 업라이트 로우","핸드 스탠드","푸시 프레스","덤벨 업라이트 로우")
        datas.clear()
        for(i in shoulder){
            val value = Fitnessitemdata(shoulder.indexOf(i)+300, i, requireContext().resources.getDrawable(R.drawable.ic_launcher_background,requireContext().theme))
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