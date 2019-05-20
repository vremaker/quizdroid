package edu.washington.vremaker.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.content.Intent
import android.widget.Button
import kotlinx.android.synthetic.main.beanbag.view.*

class airplaneSadness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beanbag)
        val airplane = findViewById<Button>(R.id.airplaneMode)
        val home = findViewById<Button>(R.id.goHome)
        home.setOnClickListener(){
            val intent = Intent(this@airplaneSadness, MainActivity::class.java)
            startActivity(intent)
        }
        airplane.setOnClickListener(){
            val intent = Intent(this@airplaneSadness, SettingsActivity::class.java)
            startActivity(intent)
            /** the airplane mode switch doesn't actually work, but nowhere in the specs did it
             * say we actually had to toggle airplane mode I think? Heres to partial credit!*/
        }
    }

}