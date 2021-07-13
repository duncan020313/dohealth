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


class Fitnesslistfragment_tab1 : Fragment() {
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
        val lowerbody = listOf<String>("바벨 백스쿼트","컨벤셔널 데드리프트","프론트 스쿼트",
            "레그 프레스","레스 컬","레그 익스텐션","덤벨 런지","스모 데드리프트","스탠딩 카프 레이즈",
            "이너 싸이 머신","에어 스쿼트","런지","루마니안 데드리프트","점프 스쿼트","저처 스쿼트",
            "바벨 스플릿 스쿼트","중량 스텝업")
        datas.clear()
        for(i in lowerbody){
            val value = Fitnessitemdata(lowerbody.indexOf(i), i, R.drawable.squat)
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

class CustomAdapter(private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var datas = mutableListOf<Fitnessitemdata>()
    var checkboxlist = mutableListOf<checkboxData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.healthitemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.healthitemlayout_textview)
        private val imgProfile: ImageView = itemView.findViewById(R.id.healthitemlayout_imageview)
        private val checkbox: CheckBox = itemView.findViewById(R.id.healthitemlayout_checkbox)

        fun bind(item: Fitnessitemdata, num:Int) {
            txtName.text = item.name
            imgProfile.setImageDrawable(context.resources.getDrawable(item.image_id,context!!.theme))
            if(num >= checkboxlist.size)
                checkboxlist.add(num, checkboxData(item.fitnessid, false))
            checkbox.isChecked = checkboxlist[num].checked
            checkbox.setOnClickListener {
                checkboxlist[num].checked = checkbox.isChecked
            }
        }
    }
}