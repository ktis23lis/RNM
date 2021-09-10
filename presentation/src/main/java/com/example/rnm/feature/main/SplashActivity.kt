package com.example.rnm.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.rnm.R

class SplashActivity : AppCompatActivity() {

    companion object{
        private const val SHOW_TIME = 2000L
    }

    private val myHandler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myHandler.postDelayed({
            startActivity(Intent(MainActivity.intent(this@SplashActivity)))
            finish()
        }, SHOW_TIME)
    }

    override fun onDestroy() {
        myHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}