package com.example.frontenddevelop

class DataLists {
}

class WorkoutList {
    var WorkoutId: Int = 0
    lateinit var date: String
    var weight: Int = 0
    var times: Int = 0
}

class ReportList {
    lateinit var date: String
    lateinit var report: MutableList<WorkoutList>
}

class PersonalReport {
    lateinit var userid : String
    lateinit var report: MutableList<ReportList>
}

class LoginResult {
    lateinit var id: String
    lateinit var nickname: String
}


class Report {
    lateinit var id: String
    lateinit var report: String
}

class dailyReport{


}