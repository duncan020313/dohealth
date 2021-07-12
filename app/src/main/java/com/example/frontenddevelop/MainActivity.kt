package com.example.frontenddevelop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.isDigitsOnly
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var calendarfragment = Calendarfragment()
    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrogitInterface
    lateinit var Maindate : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRetrofit()

        //Action bar 설정하기
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)//기본 제목을 없애줍니다.

        //Viewpager 설정하기
        val fragmentList = listOf(Rankingfragment(), calendarfragment, Groupfragment())
        val adapter = Viewpageradapter(this)
        adapter.fragmentList = fragmentList
        viewPager = findViewById(R.id.viewPager2)
        viewPager.adapter = adapter
        viewPager.currentItem = 1
        var tablayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tablayout, viewPager){tab, position->
            tab.text = when(position){
                0 -> "랭킹"
                1 -> "운동일지"
                2 -> "그룹"
                else -> "none"
            }
        }.attach()

        //intent받기
        if(intent!=null){
            val checkboxdata = intent.getParcelableArrayListExtra<checkboxData>("checkboxlist")
            if (checkboxdata != null) {
                for(i in checkboxdata){
                    if(i.checked == true){
                        Log.e("checker",i.toString())
                    }
                }
                val bundle = Bundle()
                bundle.putParcelableArrayList("checkboxlist", checkboxdata)
                calendarfragment.arguments=bundle
                Log.e("check","send data to frag")
            }
            val joingroupdata = intent.getParcelableExtra<Groupdataclass>("joingroup")
            if(joingroupdata!=null){
                groupdatalist.removeLast()
                groupdatalist.add(joingroupdata)
                val value = Groupdataclass(-1, "Add", "","",baseContext.resources.getDrawable(R.drawable.plus_profile,baseContext.theme).toBitmap())
                groupdatalist.add(value)
                groupadapter.notifyDataSetChanged()
                viewPager.currentItem = 2
            }
        }

    }

    fun addpopupstart() {
        val intent = Intent(this, Addactivity::class.java)
        startActivity(intent)
    }

    fun addgroupstart() {
        val intent = Intent(this, Addgroupactivity::class.java)
        startActivity(intent)
    }

    fun showgroupstart(item : Groupdataclass) {
        val intent = Intent(this, Showgroupactivity::class.java)
        intent.putExtra("groupdata", item)
        startActivity(intent)
    }
    fun showpopupSelectcountweight(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.selectcountweightpopuplayout, null)
        val counttext = dialogView.findViewById<EditText>(R.id.selectcountweightpopup_count)
        val weighttext = dialogView.findViewById<EditText>(R.id.selectcountweightpopup_weight)
        builder.setView(dialogView)
            .setPositiveButton("확인") { dialogInterface, i ->
                /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                if(counttext.text.toString().isDigitsOnly() && weighttext.text.toString().isDigitsOnly()){
                    val countdata = counttext.text.toString().toInt()
                    val weightdata = weighttext.text.toString().toInt()
                    val str= datas[customadapter.selectedid].inform
                    val arr = str.split("세트 수: ","세트 ","운동볼륨: ","Kg ","최대 중량: ","Kg ","총 개수: ","개")
                    val totalset = (arr[1].toInt()+1).toString()
                    val totalvolume =  (arr[3].toInt()+countdata*weightdata).toString()
                    val maxweight = if(arr[5].toInt() > weightdata) {arr[2]} else {weightdata.toString()}
                    val totalcount = (arr[7].toInt()+countdata).toString()
                    customadapter.datas[customadapter.selectedid].inform = "세트 수: "+totalset+"세트 "+"운동볼륨: "+totalvolume+"Kg "+"최대 중량: "+maxweight+"Kg "+"총 개수: "+totalcount+"개"
                    customadapter.notifyDataSetChanged()
                    postdatatoDB()
                }
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

    fun postdatatoDB(){
        val map : HashMap<String, Any> = HashMap()
        val Workout: HashMap<String, Any> = HashMap()
        val mapDailyReport : HashMap<String, Any> = HashMap()
        val inform= datas[customadapter.selectedid].inform
        val name= datas[customadapter.selectedid].name
        val workoutid= datas[customadapter.selectedid]._id
        val arr = inform.split("세트 수: ","세트 ","운동볼륨: ","Kg ","최대 중량: ","Kg ","총 개수: ","개")

        Workout.put("workoutname", name)
        Workout.put("workoutid", workoutid)
        Workout.put("totalset", arr[1])
        Workout.put("totalvolume", arr[3])
        Workout.put("maxweight", arr[5])
        Workout.put("totalcount", arr[7])

        val mapWOlist = mutableListOf(Workout)

        mapDailyReport.put("Date", date)//현재 날짜로 넣는다.
        mapDailyReport.put("WorkOutList",mapWOlist)//현재 날짜로 넣는다

        map.put("id", UserId) //id 전연변수로 선언해서 사용할것
        map.put("Report", mapDailyReport)
        map.put("date", date)
        map.put("name", name)
        map.put("workoutlist", mapWOlist)


        supplementService.postReport(map).enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Tag - FAIL", t.toString()) }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("TAG", "SUCCESS") }
        })
    }

}