package com.example.frontenddevelop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Addgroupactivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addgroupactivity)

        //Action bar 설정하기
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.addgroupactivity_toolbar);
        setSupportActionBar(toolbar);
        var actionBar = getSupportActionBar();
        actionBar?.setDisplayShowCustomEnabled(true);
        actionBar?.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar?.setDisplayHomeAsUpEnabled(true);

        //Viewpager 설정하기
        var fragmentList = listOf(Newgroupfragment(), Attendancefragment(), Volumefragment())
        val adapter = Viewpageradapter(this)
        adapter.fragmentList = fragmentList
        viewPager = findViewById(R.id.addgroupactivity_viewPager2)
        viewPager.adapter = adapter
        var tablayout = findViewById<TabLayout>(R.id.addgroupactivity_tabLayout)
        TabLayoutMediator(tablayout, viewPager){tab, position->
            tab.text = when(position){
                0 -> "신규"
                1 -> "출석률"
                2 -> "운동볼륨"
                else -> "none"
            }
        }.attach()
    }

    fun joingrouppopupstart(item : Groupdataclass){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.joingrouppopup, null)
        builder.setView(dialogView)
            .setPositiveButton("들어가기") { dialogInterface, i ->
                /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("joingroup", item)
                startActivity(intent)
                finish()
                }
            .setNegativeButton("취소") { dialogInterface, i ->
                /* 취소일 때 아무 액션이 없으므로 빈칸 */
        }
        .show()
    }
}