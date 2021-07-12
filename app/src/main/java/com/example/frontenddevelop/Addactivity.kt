package com.example.frontenddevelop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



interface OnDataPass {
    fun onDataPass(data: ArrayList<checkboxData>)
}

class Addactivity : AppCompatActivity(), View.OnClickListener, OnDataPass {
    private lateinit var closebutton: Button
    lateinit var viewPager : ViewPager2
    lateinit var checkboxlist : ArrayList<checkboxData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addactivity2)

        //Viewpager2 설정
        var fragmentList = listOf(Fitnesslistfragment_tab1(), Fitnesslistfragment_tab2(), Fitnesslistfragment_tab3(), Fitnesslistfragment_tab4(), Fitnesslistfragment_tab5(), Fitnesslistfragment_tab6())
        val adapter = Viewpageradapter(this)
        adapter.fragmentList = fragmentList
        viewPager = findViewById(R.id.addactivity2_viewPager2)
        viewPager.adapter = adapter
        var tablayout = findViewById<TabLayout>(R.id.addactivity2_tabLayout)
        TabLayoutMediator(tablayout, viewPager){tab, position->
            tab.text = when(position){
                0 -> "하체"
                1 -> "가슴"
                2 -> "등"
                3 -> "어께"
                4 -> "팔"
                5 -> "유산소"
                else -> "none"
            }
        }.attach()

        closebutton=findViewById(R.id.addactivity2_closebutton)
        closebutton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.addactivity2_closebutton){
            if(supportFragmentManager.findFragmentByTag("f0")!=null) {(supportFragmentManager.findFragmentByTag("f0") as Fitnesslistfragment_tab1).sendDatas()}
            if(supportFragmentManager.findFragmentByTag("f1")!=null) {(supportFragmentManager.findFragmentByTag("f1") as Fitnesslistfragment_tab2).sendDatas()}
            if(supportFragmentManager.findFragmentByTag("f2")!=null) {(supportFragmentManager.findFragmentByTag("f2") as Fitnesslistfragment_tab3).sendDatas()}
            if(supportFragmentManager.findFragmentByTag("f3")!=null) {(supportFragmentManager.findFragmentByTag("f3") as Fitnesslistfragment_tab4).sendDatas()}
            if(supportFragmentManager.findFragmentByTag("f4")!=null) {(supportFragmentManager.findFragmentByTag("f4") as Fitnesslistfragment_tab5).sendDatas()}
            if(supportFragmentManager.findFragmentByTag("f5")!=null) {(supportFragmentManager.findFragmentByTag("f5") as Fitnesslistfragment_tab6).sendDatas()}

            val intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("checkboxlist", checkboxlist)
            startActivity(intent)
            finish()
        }
    }
    override fun onDataPass(data : ArrayList<checkboxData>){
        checkboxlist = data
    }
}
