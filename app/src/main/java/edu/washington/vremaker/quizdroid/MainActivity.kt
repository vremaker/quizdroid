package edu.washington.vremaker.quizdroid

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.*
import org.json.JSONObject
import android.util.Log
import android.provider.Settings.System
import java.lang.Object

import org.json.JSONArray
import java.io.IOException
import android.content.SharedPreferences
import android.os.IBinder
import android.view.Menu
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.view.MenuItem
import android.net.Uri
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

// MY JSON FILE CAN BE FOUND AT https://students.washington.edu/vremaker/valerie.json
// also please ignore my poor grammar for the "there are _ questions"


class MainActivity : AppCompatActivity()  {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nService: DownloadService
    private var nServiceBound: Boolean = false
    companion object {
        private const val USER_PREF_KEY = "USER_PREFERENCES_KEY"
        private const val TIMESTAMP_KEY = "timestamp"
        val TAG: String = MainActivity::class.java.simpleName
    }

    private val WRITE_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        // start that young service here
        //check for airplane mode here
        if (isExternalStorageWritable()) {
            //check permission to write
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //have permission, can go ahead and do stuff
                Log.v(TAG, "Permission granted!")
                val intent = Intent(this@MainActivity, DownloadService::class.java)
                startService(intent)
               // saveToExternalFile()
            } else { //if we're missing permission.
                Log.v(TAG, "Permission denied!")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_REQUEST_CODE)
            }
        }


        readJson()

        var array = ArrayList<String>()
        val allTheData = QuizApp.instance.cryBoi.get()
        for (i in 0 until allTheData.size) {
            if (!array.contains(allTheData[i].topic)) {
                array.add(allTheData[i].topic)
            }
        }
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter(this, R.layout.listview_item, array)
        val listView: ListView = findViewById(R.id.listView)
        listView.setAdapter(adapter)
        listView.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val itemValue = listView.getItemAtPosition(position) as String
                val intent = Intent(this@MainActivity, all_the_things::class.java)
                intent.putExtra("topic", itemValue)
                intent.putExtra("integer", position.toString())
                startActivity(intent)
            }
        }
    }

    fun readJson() {
        val jsonString: String? = try {
            // grab file from assets folder & read it to a String
            val inputStream = assets.open(QuizApp.JSON_FILE_NAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            null
        }
        jsonString?.let {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val allTheQuestions = ArrayList<QuestionDATA>()
                val joosObject = jsonArray.get(i) as JSONObject
                val title = joosObject.get("title")
                val description = joosObject.get("desc")
                val questions = joosObject.get("questions").toString()
                val questionDataArray = ArrayList<QuestionDATA>()
                val questionArray = JSONArray(questions)
                for (i in 0 until questionArray.length()) {
                    val questObj = questionArray.get(i) as JSONObject
                    val text = questObj.get("text").toString()
                    val answer = questObj.get("answer").toString()
                    val answerString = questObj.get("answers").toString()
                    val answerArray = JSONArray(answerString)
                    val addArray = ArrayList<String>()
                    for (i in 0 until answerArray.length()) {
                        addArray.add(answerArray.get(i).toString())
                    }
                    val addMe = QuestionDATA(text, addArray, answer)
                    allTheQuestions.add(addMe)
                }
                var myTopic = Topic(
                    title.toString(),
                    "There are " + allTheQuestions.size + " questions",
                    description.toString(),
                    allTheQuestions
                )
                Log.e("KILL ", myTopic.toString())
                QuizApp.instance.cryBoi.add(myTopic)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_settings -> {
                Log.d(TAG, "Settings menu pressed")
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}




