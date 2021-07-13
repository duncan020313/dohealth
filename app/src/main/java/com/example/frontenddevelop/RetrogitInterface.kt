package com.example.frontenddevelop

import android.widget.ListView
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrogitInterface {
    @POST("/join")
    fun postRequest(@Body map : HashMap<String, Any>): Call<Void>

    @POST("/report")
    fun postReport(@Body map : HashMap<String, String>): Call<Void>

    @POST("/group")
    fun postGroup(@Body map : HashMap<String, Any>): Call<Void>

    @GET("/getnickname")
    fun getRequest(@Body id : String): Call<LoginResult> //give user Id and get user nickname

    @POST("/date")
    fun reponsedata(@Body map : HashMap<String, String>): Call<ArrayList<String>> //give date and get

    @POST("/Rank")
    fun reponseRank(@Body Rank: String): Call<ArrayList<String>> //

    @POST("/joingroup")
    fun joingroup(@Body map : HashMap<String, String>): Call<Void>
    //그룹 들어갈때, user id 랑 그룹 id 주면 -> 그룹 테이블안에 유저추가, 유저 그룹리스트 안에 추가해

    @POST("/getgroup")
    fun getGroup(@Body id :String) : Call<ArrayList<String>>
    //그룹 아이디 주면 그룹 아이디의 user 목록 받아줌

    @POST("/mygroup")
    fun myGroup(@Body id :String) : Call<ArrayList<String>>
    //그룹 아이디 주면 그룹 아이디의 user 목록 받아줌

    @POST("/creategroup")
    fun creategroup(@Body map : HashMap<String, String>) : Call<Void>
    //그룹 아이디 주면 그룹 아이디의 user 목록 받아줌

    @GET("/Rank")
    fun getRank(): Call<List<String>> //get user Id List

}