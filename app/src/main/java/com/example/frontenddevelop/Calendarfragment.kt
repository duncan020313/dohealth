package com.example.frontenddevelop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


val datas = mutableListOf<Fitnessitemdatacalendar>()
lateinit var customadapter : calendarfragment_recyclerviewadapter
class Calendarfragment : Fragment(){
    private lateinit var recyclerview : RecyclerView
    private lateinit var mCalendarView : CalendarView
    private lateinit var fab_main : FloatingActionButton
    private lateinit var fab_add : FloatingActionButton
    private lateinit var fab_remove : FloatingActionButton
    private lateinit var fab_timer : FloatingActionButton
    private lateinit var fab_open : Animation
    private lateinit var fab_close : Animation
    var isFabOpen = false
    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrogitInterface

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendarfragment, container, false)

        initRetrofit()

        //플로팅버튼 설정
        fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fab_main = view.findViewById(R.id.calendarfragment_floatingbutton_main)
        fab_add = view.findViewById(R.id.calendarfragment_floatingbutton_add)
        fab_remove = view.findViewById(R.id.calendarfragment_floatingbutton_remove)
        fab_timer = view.findViewById(R.id.calendarfragment_floatingbutton_timer)
        fab_main.setOnClickListener { view ->
            toggleFab()
        }
        fab_add.setOnClickListener { view ->
            toggleFab()
            (activity as MainActivity).addpopupstart()
        }
        fab_remove.setOnClickListener { view ->
            toggleFab()
            removeitems()
        }
        fab_timer.setOnClickListener { view ->
            toggleFab()
            (activity as MainActivity).settimerpopupstart()
        }


        //recyclerview 설정
        recyclerview = view.findViewById<RecyclerView>(R.id.calenderfragment_recyclerview)
        initRecycler()


        //calenderview 설정
        mCalendarView = view.findViewById(R.id.calendarView)
        mCalendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            // 날짜 선택 이벤트
            val date = year.toString() + "/" + (month + 1) + "/" + dayOfMonth
            refreshrecyclerview(date)
        })


        //bundle에서 데이터 받기(운동 추가하는 경우)
        //각 탭별로 운동을 구별하기 위해서 운동 id에다가 각각 100, 200, 300, .. 을 더해줘서 구별함
        val checkboxlist = arguments?.getParcelableArrayList<checkboxData>("checkboxlist")
        if (checkboxlist != null) {
            for(i in checkboxlist){
                if(datas.find{ (it.fitnessid==i.id)}!=null) continue
                var image : Int
                var name : String
                if(i.checked){
                    if(i.id>=500){
                        name = cardiovascularexercise[i.id-500]
                        image = R.drawable.running
                    } else if(i.id>=400){
                        name = arm[i.id-400]
                        image = R.drawable.arm
                    } else if(i.id>=300){
                        name = shoulder[i.id-300]
                        image = R.drawable.shoulder
                    } else if (i.id>=200){
                        name = back[i.id-200]
                        image = R.drawable.pullup
                    } else if (i.id>=100){
                        name = chest[i.id-100]
                        image = R.drawable.benchpress
                    } else{
                        name = lowerbody[i.id]
                        image = R.drawable.squat
                    }
                    val value = Fitnessitemdatacalendar(datas.size, i.id, name,"세트 수: "+"0"+"세트 "+"운동볼륨: "+"0"+"Kg "+"최대 중량: "+"0"+"Kg "+"총 개수: "+"0"+"개",image)
                    datas.add(value)
                    customadapter.notifyDataSetChanged()
                }
            }
        }

        return view
    }



    private fun initRecycler() {
        customadapter = calendarfragment_recyclerviewadapter(requireContext(), this)
        customadapter.datas = datas
        recyclerview.adapter = customadapter
    }

    private fun removeitems(){
        var removecount=0;
        for(i in customadapter.checkboxlist){
            if(i.checked){
                i.checked=false
                customadapter.checkednumber--
                datas.removeAt(customadapter.checkboxlist.indexOf(i)-removecount)
                removecount++
                customadapter.notifyDataSetChanged()
            }
        }
    }

    private fun refreshrecyclerview(date : String){
        var map : HashMap<String, String> = HashMap()
        map.put("id", "123123") // 전연변수로 설정한 아이
        map.put("date", date)

        supplementService.givedata(map).enqueue(object: Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>){
                if(response.code() == 200){
                    //rooms = response.body()!!
                }
                else if(response.code() == 404){
                    Log.d("TAG", "reposne coed 404")
                }
            }
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("TAG", "onFailure")
            }
        })

        //여기서 날짜 선택하면 그걸 액티비티로 넘겨서 DB에서 새로운 데이터를 받아오기
        //DB에는 현재 추가한 데이터 저장
        //(activity as MainActivity).getDatafromDB()
    }

    private fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrogitInterface::class.java)
    }

    fun toggleFab(){
        if (isFabOpen) {
            fab_add.startAnimation(fab_close)
            fab_remove.startAnimation(fab_close)
            fab_timer.startAnimation(fab_close)
            fab_add.setClickable(false)
            fab_remove.setClickable(false)
            fab_timer.setClickable(false)
            isFabOpen = false
        } else {
            fab_add.startAnimation(fab_open)
            fab_remove.startAnimation(fab_open)
            fab_timer.startAnimation(fab_open)
            fab_add.setClickable(true)
            fab_remove.setClickable(true)
            fab_timer.setClickable(true)
            isFabOpen = true
        }
    }
}

class calendarfragment_recyclerviewadapter(private val context: Context, private val fragment : Calendarfragment) : RecyclerView.Adapter<calendarfragment_recyclerviewadapter.ViewHolder>() {

    var datas = mutableListOf<Fitnessitemdatacalendar>()
    var checkboxlist = mutableListOf<checkboxData>()
    var selectedid = -1
    var checkednumber = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.calendarfragment_healthitemlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = itemView.findViewById(R.id.calendarfraghealthitem_name)
        private val inform: TextView = itemView.findViewById(R.id.calendarfraghealthitem_imformation)
        private val imgProfile: ImageView = itemView.findViewById(R.id.calendarfraghealthitem_imageview)
        private val checkbox: CheckBox = itemView.findViewById(R.id.calendarfraghealthitem_checkbox)

        fun bind(item: Fitnessitemdatacalendar, num:Int) {
            name.text = item.name
            inform.text = item.inform
            imgProfile.setImageDrawable(context.resources.getDrawable(item.image_id, context.theme))
            if(num >= checkboxlist.size)
                checkboxlist.add(num, checkboxData(item._id, false))
            checkbox.isChecked = checkboxlist[num].checked
            checkbox.setOnClickListener {
                if(checkednumber == 0){
                    fragment.toggleFab()
                }
                else if(checkednumber == 1 && checkbox.isChecked == false && fragment.isFabOpen == true){
                    fragment.toggleFab()
                }
                if(checkbox.isChecked == true) checkednumber++
                else checkednumber--
                checkboxlist[num].checked = checkbox.isChecked
            }
            itemView.setOnClickListener{
                selectedid = num
                (context as MainActivity).showpopupSelectcountweight(item.name)
            }
        }
    }
}