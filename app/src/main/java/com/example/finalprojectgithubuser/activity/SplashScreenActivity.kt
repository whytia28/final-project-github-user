package com.example.finalprojectgithubuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.finalprojectgithubuser.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.title = getString(R.string.welcome_title)

        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashScreenActivity, MainActivity::class.java
                )
            )
            finish()
        }, 3000)
    }
}