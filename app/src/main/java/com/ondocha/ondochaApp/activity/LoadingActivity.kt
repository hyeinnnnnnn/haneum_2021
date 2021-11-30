package com.ondocha.ondochaApp.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.ondocha.ondochaApp.R

class LoadingActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        //버전업하면서 안쓰이는 기술이 된것
        Handler().postDelayed({

            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }

}