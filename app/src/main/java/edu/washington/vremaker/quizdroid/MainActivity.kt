package edu.washington.vremaker.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.*


var array = arrayOf("Math", "Physics", "Marvel Super Heroes")


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter(this, R.layout.listview_item, array)
        val listView: ListView = findViewById(R.id.listView)
        listView.setAdapter(adapter)
        listView.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val itemValue = listView.getItemAtPosition(position) as String
                val intent = Intent(this@MainActivity, all_the_things::class.java)
                intent.putExtra("topic", itemValue)
                startActivity(intent)
            }
        }
    }
}



