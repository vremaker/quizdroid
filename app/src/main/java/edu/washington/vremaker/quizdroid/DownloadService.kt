package edu.washington.vremaker.quizdroid

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.os.Binder
import android.content.Context
import android.os.IBinder
import android.widget.Toast
import android.widget.EditText
import android.R
import android.content.ComponentName
import android.widget.LinearLayout
import android.view.Gravity
import android.preference.PreferenceManager
import android.content.SharedPreferences



class DownloadService: IntentService("DownloadService") {

    val TAG = "DownloadService"
    private lateinit var mHandler: Handler
    var download = false

    override fun onCreate() {
        Log.v(TAG, "Service started")

        mHandler = Handler()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "Service started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.v(TAG, "Handling Intent")
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val url =  prefs.getString("Url", "bobhttps://students.washington.edu/vremaker/valerie.json")
        val interval = prefs.getString("interval", "10").toInt() * 60000
        Log.e("ope", interval.toString())
        download = true
        while (download) {
            mHandler.post {
                var toast = Toast.makeText(this@DownloadService, "" + url  , Toast.LENGTH_SHORT).show() // get the url from preferences

            }
            try {
                Thread.sleep(interval.toLong()) // get the time from preferences
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onDestroy() {
        Log.v(TAG, "Service finished")
        download = false
        super.onDestroy()
    }

}
