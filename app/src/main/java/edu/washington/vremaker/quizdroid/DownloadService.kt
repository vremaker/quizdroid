package edu.washington.vremaker.quizdroid

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.support.v4.content.FileProvider
import android.widget.Toast
import android.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.*
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.Settings
import android.support.annotation.RequiresApi
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter


class DownloadService : IntentService("DownloadService") {

    val TAG = "DownloadService"
    private lateinit var mHandler: Handler
    private val FILE_NAME = "questions.json"

    var download = false

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            /* API 17 and above */
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            /* below */
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    override fun onCreate() {
        Log.v(TAG, "Service started")
        mHandler = Handler()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "Service started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.v(TAG, "Service finished")
        download = false
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onHandleIntent(intent: Intent?) {
        Log.v(TAG, "Handling Intent")
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val interval = prefs.getString("interval", "10").toInt() * 60000
        download = true
        while (download) {
            mHandler.post {
                setUpVolleyFetching()
            }
            try {
                Thread.sleep(/*interval.toLong()*/ 9000) // get the time from preferences
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setUpVolleyFetching() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val url = prefs.getString("Url", "http//tednewardsandbox.site44.com/questions.json")
        val dest = "questions.json"
        Log.e("URL", url)
        // Request a string response from the provided URL.
        if (isNetworkAvailable(this)) {
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> {
                    //
                    try {
                        val dir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        if (!dir.exists()) {
                            dir.mkdirs() //make Documents directory if doesn't otherwise exist << emulator workaround
                        }
                        val file = File(dir, FILE_NAME)
                        Log.v(TAG, "Saving to  " + file.absolutePath)
                        if(file.exists()) {
                            file.delete()
                        }
                        val out = PrintWriter(FileWriter(file))
                        out.println(it.toString())
                        out.close()

                    } catch (ioe: IOException) {
                        Log.d(TAG, Log.getStackTraceString(ioe))
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this@DownloadService, "That didn't work!", Toast.LENGTH_SHORT).show()
                }
            )
            //check to see if another one is running!
            queue.add(stringRequest)

        } else {
            if (isAirplaneModeOn(this)) {
              //intent to the dialog frag
                //val intent = Intent(this@DownloadService, showDiag::class.java)
                //startActivity(intent)


            } else {
                Toast.makeText(this@DownloadService, "You don't have access to the internet", Toast.LENGTH_LONG).show()
            }
        }
    }
}
