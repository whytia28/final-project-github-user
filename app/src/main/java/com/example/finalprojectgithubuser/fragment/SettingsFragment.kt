package com.example.finalprojectgithubuser.fragment

import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.TimePicker
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.alarm.AlarmReceiver
import com.example.finalprojectgithubuser.alarm.SharedPref
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    TimePickerDialog.OnTimeSetListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var simpleDateFormat: SimpleDateFormat

    private var reminderSwitch: SwitchPreference? = null
    private var reminderTimePreference: Preference? = null
    private var reminderLanguagePreference: Preference? = null
    private var hours = 0
    private var minutes = 0

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preference, rootKey)
        init()
        setSummaries()
        setReminderSwitch()
    }

    private fun setReminderSwitch() {
        when (sharedPreferences.getBoolean("reminder_switch", false)) {
            true -> reminderTimePreference?.isVisible = true
            false -> reminderTimePreference?.isVisible = false
        }
    }

    private fun init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        reminderSwitch = findPreference("reminder_switch")
        reminderTimePreference = findPreference("reminder_time")
        reminderLanguagePreference = findPreference("language")
        reminderTimePreference?.onPreferenceClickListener = this
        reminderLanguagePreference?.onPreferenceClickListener = this
        simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            "reminder_time" -> {
                hours = SharedPref(requireContext()).getHour()
                minutes = SharedPref(requireContext()).getMinute()
                TimePickerDialog(context, this, hours, minutes, true).show()
            }
            "language" -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        SharedPref(requireContext()).setHour(hourOfDay)
        SharedPref(requireContext()).setMinute(minute)
        setSummaries()

        AlarmReceiver().setReminder(requireContext())
    }

    private fun setSummaries() {
        reminderTimePreference?.summary =
            simpleDateFormat.format(SharedPref(requireContext()).getHourAndMinute())
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "reminder_switch" -> {
                when (sharedPreferences?.getBoolean("reminder_switch", false)) {
                    true -> {
                        reminderTimePreference?.isVisible = true
                        AlarmReceiver().setReminder(requireContext())
                    }
                    false -> {
                        reminderTimePreference?.isVisible = false
                        AlarmReceiver().cancelReminder(requireContext())
                    }
                }
            }
        }
    }
}