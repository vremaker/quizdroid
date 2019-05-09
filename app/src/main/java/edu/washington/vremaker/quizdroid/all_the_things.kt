package edu.washington.vremaker.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.*
import org.w3c.dom.Text
import android.util.Log

var numberCompleted = 0
var numberCorrect = 0
var answerKeep = 0

class all_the_things : AppCompatActivity(), topic_overview.startListener, answer.whatNextListener, question.answerListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var topic = intent.getStringExtra("topic")

        val topicFrag = topic_overview.newInstance(topic)
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragHere, topicFrag, "TOPIC FRAG")
            addToBackStack(null)
            commit()
        }
        setContentView(R.layout.all_the_things)
    }

    override fun toQuestion (topic: String) {
        var topic = intent.getStringExtra("topic")
        Log.e("in all", topic)
        val questionFrag = question.newInstance(topic, numberCorrect.toString(), numberCompleted.toString(), answerKeep.toString())
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere, questionFrag,"QUESTION FRAG" )
            addToBackStack(null)
            commit()
        }
    }

    override fun toAnswer(topic: String, your: String, correct: String, numCorrect: String, numTotal:String){
        var topic = intent.getStringExtra("topic")
        val answerFrag = answer.newInstance(topic, your, correct, numCorrect, numTotal)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere,answerFrag, "ANSWER FRAG")
            addToBackStack(null)
            commit()
        }
    }

    override fun whatNext(topic: String, complete: String) {
        var topic = intent.getStringExtra("topic")
        if(complete.equals("2")) {
            var intent = Intent(this@all_the_things, MainActivity::class.java)
            startActivity(intent)
            numberCompleted = 0
            numberCorrect = 0
            answerKeep = 0
        } else {
            val questionFrag = question.newInstance(topic,numberCorrect.toString(), numberCompleted.toString(), answerKeep.toString())
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragHere,questionFrag, "ANSWER FRAG")
                addToBackStack(null)
                commit()
            }
        }
    }

}
