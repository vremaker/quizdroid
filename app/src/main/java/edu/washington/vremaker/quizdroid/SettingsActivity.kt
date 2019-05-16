package edu.washington.vremaker.quizdroid

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences




class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        //the FM is the guy who moves fragments around
        fragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }



    class SettingsFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            addPreferencesFromResource(R.xml.preferences)
        }
    }
}