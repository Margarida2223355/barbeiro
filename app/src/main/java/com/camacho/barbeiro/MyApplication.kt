package com.camacho.barbeiro

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        private lateinit var sInstance: MyApplication

        fun getAppContext(): Context {
            return sInstance.applicationContext
        }

        var userPermission = ""
        val BASE_URL = "api_url"
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}