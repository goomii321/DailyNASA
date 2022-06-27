package com.linda.dailynasa.common

import com.linda.dailynasa.DailyNASAApp
import com.linda.dailynasa.R

enum class CurrentFragmentType(val value:String) {
    HOME(DailyNASAApp.instance.getString(R.string.menu_home)),
    GALLERY(DailyNASAApp.instance.getString(R.string.menu_gallery))
}