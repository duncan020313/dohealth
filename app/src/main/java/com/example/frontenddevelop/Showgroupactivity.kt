package com.example.frontenddevelop

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Showgroupactivity : AppCompatActivity() {
    private lateinit var recyclerview : RecyclerView
    private lateinit var mCalendarView : CalendarView
    private lateinit var fab_main : FloatingActionButton
    private lateinit var fab_add : FloatingActionButton
    private lateinit var fab_remove : FloatingActionButton
    private lateinit var fab_exit : FloatingActionButton
    private lateinit var fab_open : Animation
    private lateinit var fab_close : Animation
    val groupuserdata = mutableListOf<Userdataclass>()
    private lateinit var useradapter : Useradapter
    private lateinit var groupdata : Groupdataclass
    private var isFabOpen = false
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showgroupactivity)

        Log.e("Break","Log4")
        onPause()
        //intent받기
        if(intent!=null){
            val gd = intent.getParcelableExtra<Groupdataclass>("groupdata")
            if (gd != null) {
                groupdata = gd
                val groupname = findViewById<TextView>(R.id.showgroupactivity_groupname)
                groupname.text = groupdata.name
            }
        }


        //플로팅버튼 설정
        fab_open = AnimationUtils.loadAnimation(baseContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(baseContext, R.anim.fab_close);
        fab_exit = findViewById(R.id.showgroupactivity_floatingbutton_exit)
        fab_main = findViewById(R.id.showgroupactivity_floatingbutton_main)
        fab_add = findViewById(R.id.showgroupactivity_floatingbutton_add)
        fab_remove = findViewById(R.id.showgroupactivity_floatingbutton_remove)
        fab_main.setOnClickListener { view ->
            toggleFab()
        }
        fab_add.setOnClickListener { view ->
            toggleFab()
            //멤버 추가
        }
        fab_remove.setOnClickListener { view ->
            toggleFab()
            //삭제
            finish()
        }
        fab_exit.setOnClickListener { view ->
            //탈퇴 기능
            toggleFab()
            groupdatalist.removeIf{
                it._id == groupdata._id
            }
            groupadapter.notifyDataSetChanged()
            finish()
        }

        //calenderview 설정
        mCalendarView = findViewById(R.id.showgroupactivity_calendarView)
        mCalendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            // 날짜 선택 이벤트
            val date = year.toString() + "/" + (month + 1) + "/" + dayOfMonth
            refreshrecyclerview(date)
        })

        //recyclerview 설정
        recyclerview = findViewById<RecyclerView>(R.id.showgroupactivity_recyclerview)
        initRecycler()
    }

    private fun toggleFab(){
        if (isFabOpen) {
            fab_add.startAnimation(fab_close)
            fab_remove.startAnimation(fab_close)
            fab_exit.startAnimation(fab_close)
            fab_add.setClickable(false)
            fab_remove.setClickable(false)
            fab_exit.setClickable(false)
            isFabOpen = false
        } else {
            fab_add.startAnimation(fab_open)
            fab_remove.startAnimation(fab_open)
            fab_exit.startAnimation(fab_open)
            fab_add.setClickable(true)
            fab_remove.setClickable(true)
            fab_exit.setClickable(true)
            isFabOpen = true
        }
    }

    private fun initRecycler() {
        useradapter = Useradapter(baseContext)
        val userdata = Userdataclass(-1, "초기값", "정보", "소개", R.drawable.user)
        groupuserdata.add(userdata)
        useradapter.userdatalist = groupuserdata
        recyclerview.adapter = useradapter
    }

    private fun refreshrecyclerview(date : String){
        //여기서 날짜 선택하면 그걸 액티비티로 넘겨서 DB에서 새로운 데이터를 받아오기
        //DB에는 현재 추가한 데이터 저장
        //(activity as MainActivity).getDatafromDB()
    }
}

class Useradapter(private val context: Context) : RecyclerView.Adapter<Useradapter.ViewHolder>() {
    var userdatalist = mutableListOf<Userdataclass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.useritemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userdatalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userdatalist[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val username: TextView = itemView.findViewById(R.id.useritemlayout_username)
        private val userinform: TextView = itemView.findViewById(R.id.useritemlayout_inform)
        private val userimage: ImageView = itemView.findViewById(R.id.useritemlayout_image)
        private  val userintro : TextView = itemView.findViewById(R.id.useritemlayout_introduce)

        fun bind(item: Userdataclass, num:Int) {
            username.text = item.name
            userinform.text = item.inform
            userimage.setImageDrawable(context.resources.getDrawable(item.profileid,context!!.theme))
            userintro.text = item.intro
            itemView.setOnClickListener{
                Log.e("userfrag",num.toString())
            }
        }
    }
}