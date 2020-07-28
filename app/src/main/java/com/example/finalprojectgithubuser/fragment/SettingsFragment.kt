package com.example.finalprojectgithubuser.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.finalprojectgithubuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)
    }
}