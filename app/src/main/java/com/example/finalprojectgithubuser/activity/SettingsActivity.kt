package com.example.finalprojectgithubuser.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.setting_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}