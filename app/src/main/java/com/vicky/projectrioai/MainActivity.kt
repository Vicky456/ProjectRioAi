package com.vicky.projectrioai

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        thread(start = true)  {
            Thread.sleep(3000)
            startActivity(Intent(applicationContext,HomeActivity::class.java))
            finish()
        }

    }
}
