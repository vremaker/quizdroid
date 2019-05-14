package edu.washington.vremaker.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class PreferencesActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.settings)
    }
}