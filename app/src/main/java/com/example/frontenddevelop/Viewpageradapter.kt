package com.example.frontenddevelop

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Viewpageradapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<Fragment>()
    override fun getItemCount(): Int {
        return fragmentList.count()
    }
    override fun createFragment(position: Int): Fragment {
        Log.e("Tab: ", position.toString())
        return fragmentList.get(position)
    }
}