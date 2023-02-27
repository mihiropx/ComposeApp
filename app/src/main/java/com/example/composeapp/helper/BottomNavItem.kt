package com.example.composeapp.helper

import com.example.composeapp.R

sealed class BottomNavItem(val title:Int, val icon:Int, val screenRoute:String){
    object Explore:BottomNavItem(R.string.explore_tab,R.drawable.ic_explore,"explore")
    object Storage:BottomNavItem(R.string.storage_tab,R.drawable.ic_storage,"storage")
    object Settings:BottomNavItem(R.string.settings_tab,R.drawable.ic_settings,"settings")
}
