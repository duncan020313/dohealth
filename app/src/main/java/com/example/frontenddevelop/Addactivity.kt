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
    val lowerbody = listOf<String>("바벨 백스쿼트","컨벤셔널 데드리프트","프론트 스쿼트",
        "레그 프레스","레스 컬","레그 익스텐션","덤벨 런지","스모 데드리프트","스탠딩 카프 레이즈",
        "이너 싸이 머신","에어 스쿼트","런지","루마니안 데드리프트","점프 스쿼트","저처 스쿼트",
        "바벨 스플릿 스쿼트","중량 스텝업")
    val chest = listOf<String>("벤치 프레스","인클라인 벤치프레스","덤벨 벤치프레스","딥스",
        "덤벨플라이","케이플 크로스오버","체스트 프레스 머신","펙덱 플라이 머신",
        "푸시업","인클라인 덤벨 플라이","덤벨 풀오버","인클라인 벤치프레스 머신",
        "중량 딥스","중량 푸시업","힌두 푸시업","아처 푸시업")
    val back = listOf<String>("풀업","바벨 로우","덤벨 로우","펜들레이 로우","시티드 로우 머신","렛풀 다운"
        ,"친업","백 익스텐션","시티드 케이블 로우","원암 덤벨 로우","중량 풀업","인클라인 바벨 로우","인버티드 로우",
        "바벨 풀오버","백 익스텐션","중량 하이퍼 익스텐션")
    val shoulder = listOf<String>("오버헤드 프레스","덤벨 숄더 프레스","덤벨 레터럴 레이즈","덤벨 프론트 레이즈",
        "덤벨 슈러그","비하인드 넥 프레스","페이스 풀","핸드스탠드 푸시업","케이블 리버스 플라이",
        "바벨 업라이트 로우","벤트오버 덤벨 레터럴 레이즈","아놀드 덤벨 프레스",
        "숄더 프레스 머신","이지바 업라이트 로우","핸드 스탠드","푸시 프레스","덤벨 업라이트 로우")
    val arm = listOf<String>("바벨 컬","덤벨 컬","덤벨 삼두 익스텐션","덤벨 킥백","덤벨 리스트 컬","덤벨 해머 컬",
        "케이블 푸시 다운","클로즈그립 푸시업","이지바 컬","케이블 컬","케이블 삼두 익스텐션","시티드 덤벨 익스텐션",
        "스컬 크러셔","바벨 리스트 컬")
    val cardiovascularexercise = listOf<String>("트레드 밀","싸이클","로잉 머신")
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

            for(i in checkboxlist){
                if(fitnessitemdatas.find{ (it.fitnessid==i.id)}!=null) continue
                var image : Int
                var name : String
                if(i.checked) {
                    if (i.id >= 500) {
                        name = cardiovascularexercise[i.id - 500]
                        image = R.drawable.running
                    } else if (i.id >= 400) {
                        name = arm[i.id - 400]
                        image = R.drawable.arm
                    } else if (i.id >= 300) {
                        name = shoulder[i.id - 300]
                        image = R.drawable.shoulder
                    } else if (i.id >= 200) {
                        name = back[i.id - 200]
                        image = R.drawable.pullup
                    } else if (i.id >= 100) {
                        name = chest[i.id - 100]
                        image = R.drawable.benchpress
                    } else {
                        name = lowerbody[i.id]
                        image = R.drawable.squat
                    }
                    val value = Fitnessitemdatacalendar(
                        fitnessitemdatas.size,
                        i.id,
                        name,
                        "세트 수: " + "0" + "세트 " + "운동볼륨: " + "0" + "Kg " + "최대 중량: " + "0" + "Kg " + "총 개수: " + "0" + "개",
                        image
                    )
                    fitnessitemdatas.add(value)
                    fitnessitemcustomadapter.notifyDataSetChanged()
                }
            }
            finish()
        }
    }
    override fun onDataPass(data : ArrayList<checkboxData>){
        checkboxlist = data
    }
}
