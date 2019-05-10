package edu.washington.vremaker.quizdroid
import android.app.Application
import android.util.Log
import java.io.Serializable

class QuizApp: Application() {
    val TAG = "QuizApp"

    lateinit var cryBoi: Crying
        private set

    companion object {
        lateinit var instance: QuizApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "QuizApp is being loaded and run")
        instance = this
        cryBoi = Crying()
        //cryBoi.storeData()
    }
}

data class QuestionDATA(val question: String, val choices: Array<String>, val correct: Int): Serializable
data class Topic(val topic:String, val short: String, val long: String, val questions: Array<QuestionDATA> ): Serializable

interface TopicRepository {
    fun add(topic:Topic)
    fun remove(topic: Topic)
    fun get(): ArrayList<Topic>
}
