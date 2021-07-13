package com.example.frontenddevelop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Addgroupactivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var fab_main : FloatingActionButton
    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrogitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addgroupactivity)
        initRetrofit()

        //Action bar 설정하기
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.addgroupactivity_toolbar);
        setSupportActionBar(toolbar);
        var actionBar = getSupportActionBar();
        actionBar?.setDisplayShowCustomEnabled(true);
        actionBar?.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
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
        fab_main = findViewById(R.id.addgroupactivity_floatingbutton_main)
        fab_main.setOnClickListener { view ->
            showcreategrouppopup()
        }
        fab_main.setColorFilter(R.color.white)
    }

    fun joingrouppopupstart(item : Groupdataclass){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.joingrouppopup, null)
        builder.setView(dialogView)
            .setPositiveButton("들어가기") { dialogInterface, i ->
                /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                if(groupdatalist.find{
                    it._id == item._id
                    }!=null){Toast.makeText(this, "이미 가입된 그룹입니다", Toast.LENGTH_SHORT).show()
                }
                else{
                    groupdatalist.removeLast()
                    groupdatalist.add(item)
                    val value = Groupdataclass(-1, "Add", "","",R.drawable.plus)
                    groupdatalist.add(value)
                    groupadapter.notifyDataSetChanged()

                    var map : HashMap<String, String> = HashMap()
                    map.put("id", UserId) // 전연변수로 설정한 아이
                    map.put("groupid", item._id.toString())

                    supplementService.joingroup(map).enqueue(object:
                        Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>){
                            if(response.code() == 200 ) {Log.d("TAG", "join well")}
                            else {Log.d("TAG", "Already you are in the group") }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.d("TAG", t.toString())
                        }
                    })
                    Toast.makeText(this, "가입되었습니다", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("취소") { dialogInterface, i ->
                /* 취소일 때 아무 액션이 없으므로 빈칸 */
        }
        .show()
    }

    fun showcreategrouppopup(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.creategrouppopup, null)
        val groupname_text = dialogView.findViewById<EditText>(R.id.creategrouppopup_name)
        val groupnumber_text = dialogView.findViewById<EditText>(R.id.creategrouppopup_number)
        val groupthreshold_text = dialogView.findViewById<EditText>(R.id.creategrouppopup_threshold)
        val groupprofile_imagebutton = dialogView.findViewById<ImageButton>(R.id.creategrouppopup_profile)
        val groupintro = dialogView.findViewById<EditText>(R.id.creategrouppopup_intro)
        groupprofile_imagebutton.setOnClickListener {
            //이미지 불러오기
            Log.e("clicked","이미지 가져오기")
        }
        builder.setView(dialogView)
            .setPositiveButton("생성하기") { dialogInterface, i ->
                /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                val groupname = groupname_text.text.toString()
                val groupnumber = groupnumber_text.text.toString()
                val groupthreshold = groupthreshold_text.text.toString()
                val image = R.drawable.group //원래는 이거 대신에 이미지 가져와야됨
                val groupintro = groupintro.text.toString()
                //DB에 그룹 데이터 추가해야됨
<<<<<<< HEAD


                val newgroup = Groupdataclass(groupname.hashCode(), groupname, "최대 인원: "+groupnumber+"명 하루 목표: "+groupthreshold+"세트", "소개글", image)
                groupdatalist.removeLast()
                groupdatalist.add(newgroup)

                val value = Groupdataclass(-1, "Add", "","",R.drawable.plus)

=======
                val newgroup = Groupdataclass(groupname.hashCode(), groupname, "최대 인원: "+groupnumber+"명 하루 목표: "+groupthreshold+"세트", groupintro, image)
>>>>>>> 2f866dd5f546fb930e7257d5c4a2c1a8cd7b0613
                Log.d("Breakpoint 1","")
                var map : HashMap<String, String> = HashMap()

                map.put("groupid", groupname.hashCode().toString())
                map.put("groupname", groupname)
                map.put("groupnumber", groupnumber)
                map.put("groupthreshold", groupthreshold)
                map.put("id", UserId)

                supplementService.creategroup(map).enqueue(object: Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("TAG - FAIL", t.toString()) }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("TAG", "SUCCESS") }
                })
<<<<<<< HEAD


=======
                groupdatalist.removeLast()
                groupdatalist.add(newgroup)
                val value = Groupdataclass(-1, "Add", "","",R.drawable.plus)
>>>>>>> 2f866dd5f546fb930e7257d5c4a2c1a8cd7b0613
                groupdatalist.add(value)
                groupadapter.notifyDataSetChanged()
                Log.d("Breakpoint 2","")



                finish()
            }
            .setNegativeButton("취소") { dialogInterface, i ->
                /* 취소일 때 아무 액션이 없으므로 빈칸 */
            }
            .show()
    }

    private fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrogitInterface::class.java)
    }

}