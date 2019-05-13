package edu.washington.vremaker.quizdroid

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import java.io.Serializable
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.json.JSONObject
import org.json.JSONArray
import java.io.File
import java.io.IOException


class QuizApp: Application() {
    val TAG = "QuizApp"

    lateinit var cryBoi: Crying
        private set

    companion object {
        lateinit var instance: QuizApp
            private set
        const val JSON_FILE_NAME = "questions.json"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "QuizApp is being loaded and run")
        instance = this
        cryBoi = Crying()

    }
}




//parse json, create question and topic objects and the add the to the repository and then select based on indexes?
data class QuestionDATA(val question: String, val choices: ArrayList<String>, val correct: String): Serializable
data class Topic(val topic:String, val short: String, val long: String, val questions: ArrayList<QuestionDATA> ): Serializable

interface TopicRepository {
    fun add(topic:Topic)
    fun remove(topic: Topic)
    fun get(): ArrayList<Topic>
}


