package com.example.rnm.app

import android.app.Application
import com.example.rnm.di.Injector

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Injector.initApplicationComponent(this)
    }
}