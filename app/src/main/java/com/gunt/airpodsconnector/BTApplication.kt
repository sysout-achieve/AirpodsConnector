package com.gunt.airpodsconnector

import android.app.Application
import android.content.Context

class BTApplication :Application() {

    companion object {
        lateinit var instance: BTApplication
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}