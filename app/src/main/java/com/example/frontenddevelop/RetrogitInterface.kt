package com.example.frontenddevelop

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrogitInterface {
    @POST("/join")
    fun postRequest(@Body map : HashMap<String, Any>): Call<Void>

    @POST("/report")
    fun postReport(@Body map : HashMap<String, Any>): Call<Void>

    @POST("/group")
    fun postGroup(@Body map : HashMap<String, Any>): Call<Void>

    @GET("/getnickname")
    fun getRequest(@Body id : String): Call<LoginResult> //give user Id and get user nickname

    @GET("/date")
    fun givedata(@Body map : HashMap<String, String>): Call<List<String>> //give date and get

    @GET("/Rank")
    fun getRank(): Call<List<String>> //get user Id List

}