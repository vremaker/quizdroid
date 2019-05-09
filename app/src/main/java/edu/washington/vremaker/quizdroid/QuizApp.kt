package edu.washington.vremaker.quizdroid
import android.app.Application
import android.util.Log

class QuizApp: Application() {
    val TAG = "QuizApp"
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "QuizApp is being loaded and run")
    }
}