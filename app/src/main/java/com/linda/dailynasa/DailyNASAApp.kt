package com.linda.dailynasa

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class DailyNASAApp:Application() {

    companion object {
        var instance: DailyNASAApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}