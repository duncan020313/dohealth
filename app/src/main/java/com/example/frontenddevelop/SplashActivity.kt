package com.example.frontenddevelop

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceStare: Bundle?) {
        super.onCreate(savedInstanceStare)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(applicationContext, global::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}