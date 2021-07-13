package com.example.frontenddevelop

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var calendarfragment = Calendarfragment()
    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrogitInterface
    lateinit var Maindate : String
    private var settimer = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRetrofit()

        //Action bar 설정하기
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setActionBar(toolbar)
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
            tab.icon = when(position){
                0 -> getDrawable(R.drawable.rank)
                1 -> getDrawable(R.drawable.journal)
                2 -> getDrawable(R.drawable.group)
                else -> null
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

                viewPager.currentItem = 2
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Log.e("Homebutton", "Clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addpopupstart() {
        val intent = Intent(this, Addactivity::class.java)
        startActivity(intent)
        finish()
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
    fun showpopupSelectcountweight(name : String){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.selectcountweightpopuplayout, null)
        val title = dialogView.findViewById<TextView>(R.id.selectcountweightpopup_textview)
        val counttext = dialogView.findViewById<EditText>(R.id.selectcountweightpopup_count)
        val weighttext = dialogView.findViewById<EditText>(R.id.selectcountweightpopup_weight)
        title.text = name
        builder.setView(dialogView)
            .setPositiveButton("확인") { dialogInterface, i ->
                /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                if(counttext.text.toString()!="" && weighttext.text.toString() !=""){
                    if(counttext.text.toString().isDigitsOnly() && weighttext.text.toString().isDigitsOnly()){
                        val countdata = counttext.text.toString().toInt()
                        val weightdata = weighttext.text.toString().toInt()
                        val str= datas[customadapter.selectedid].inform
                        val arr = str.split("세트 수: ","세트 ","운동볼륨: ","Kg ","최대 중량: ","Kg ","총 개수: ","개")
                        val totalset = (arr[1].toInt()+1).toString()
                        val totalvolume =  (arr[3].toInt()+countdata*weightdata).toString()
                        val maxweight = if(arr[5].toInt() > weightdata) {arr[5]} else {weightdata.toString()}
                        val totalcount = (arr[7].toInt()+countdata).toString()
                        customadapter.datas[customadapter.selectedid].inform = "세트 수: "+totalset+"세트 "+"운동볼륨: "+totalvolume+"Kg "+"최대 중량: "+maxweight+"Kg "+"총 개수: "+totalcount+"개"
                        customadapter.notifyDataSetChanged()
                        postdatatoDB()
                        //starttimerpopup()
                    }
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
        val map : HashMap<String, String> = HashMap()
        val Workout: HashMap<String, String> = HashMap()
        val inform= datas[customadapter.selectedid].inform
        val name= datas[customadapter.selectedid].name
        val workoutid= datas[customadapter.selectedid]._id
        //val arr = inform.split("세트 수: ","세트 ","운동볼륨: ","Kg ","최대 중량: ","Kg ","총 개수: ","개")

        //Workout.put("workoutname", name.toString())
        //Workout.put("workoutid", workoutid.toString())
        //Workout.put("totalset", arr[1].toString())
        //Workout.put("totalvolume", arr[3].toString())
        //Workout.put("maxweight", arr[5].toString())
        //Workout.put("totalcount", arr[7].toString())

        map.put("id", UserId) //id 전연변수로 선언해서 사용할것
        map.put("Report", (date+"#"+ inform+"#"+name+"#"+workoutid).toString())

        map.put("date", date)
        map.put("name", name)


        supplementService.postReport(map).enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Tag - FAIL", t.toString()) }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("TAG", "SUCCESS") }
        })
    }

    fun starttimerpopup(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.timerpopuplayout, null)
        val alterdialog = builder.create()
        val minute = dialogView.findViewById<TextView>(R.id.timerpopuplayout_minute)
        val second = dialogView.findViewById<TextView>(R.id.timerpopuplayout_second)

        builder.setView(dialogView)
            .setNegativeButton("종료") { dialogInterface, i ->

            }.show()
        val timer = object: CountDownTimer((settimer*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min = millisUntilFinished/60000
                val sec = (millisUntilFinished%60000)/1000
                minute.text = min.toString()
                second.text = sec.toString()
            }

            override fun onFinish() {alterdialog.dismiss()}
        }
        Log.e("time", "start")
        timer.start()
    }

    fun settimerpopupstart(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.timersettingpopuplayout, null)
        val minute = dialogView.findViewById<EditText>(R.id.timersettingpopuplayout_minute)
        val second = dialogView.findViewById<EditText>(R.id.timersettingpopuplayout_second)
        builder.setView(dialogView)
            .setPositiveButton("설정하기") { dialogInterface, i ->
                if(minute.text.toString().toInt()>60 || second.text.toString().toInt()>60){
                    Toast.makeText(this, "설정 실패", Toast.LENGTH_SHORT).show()
                }
                else {settimer = minute.text.toString().toInt()*60+second.text.toString().toInt()}
                Log.e("settimer",settimer.toString())
            }
            .setNegativeButton("취소") { dialogInterface, i ->

            }
            .show()
    }
}