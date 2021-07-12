package com.example.frontenddevelop

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Fitnessitemdata(
    val fitnessid: Int,
    val name: String = "",
    val image_id: Int)

@Parcelize
data class checkboxData(
    var id: Int,
    var checked: Boolean) : Parcelable

//val datas = mutableListOf<Fitnessitemdatacalendar>()
data class Fitnessitemdatacalendar(
    val _id: Int, //datas.size //
    val fitnessid: Int, //운동id
    val name: String = "",//운동이름
    var inform: String = "0/0/0/0", //"세트 수/운동볼륨/최대 중량/총 개
    val image_id: Int)

@Parcelize
data class Groupdataclass(
    val _id: Int,
    val name: String = "",
    val inform: String = "",
    val intro: String = "",
    val imageid: Int,
) : Parcelable

@Parcelize
data class Userdataclass(
    val _id: Int,
    val name: String,
    val inform: String,
    val intro: String,
    val profileid: Int
): Parcelable