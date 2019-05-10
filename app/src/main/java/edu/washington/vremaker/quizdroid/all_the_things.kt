package edu.washington.vremaker.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import java.io.Serializable
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.*
import org.w3c.dom.Text

var numberCompleted = 0
var numberCorrect = 0
var answerKeep = 0

class all_the_things : AppCompatActivity(), topic_overview.startListener, answer.whatNextListener, question.answerListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var allTheData = QuizApp.instance.cryBoi.get()
        var topic = intent.getStringExtra("topic")
        storeData(topic)
        val topicFrag = topic_overview.newInstance(topic)
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragHere, topicFrag, "TOPIC FRAG")
            addToBackStack(null)
            commit()
        }
        setContentView(R.layout.all_the_things)
    }

    override fun toQuestion (topic: String) {
        val allTheData= QuizApp.instance.cryBoi.get()
        val questionFrag = question.newInstance(allTheData[0].topic,  numberCorrect.toString(), numberCompleted.toString())
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere, questionFrag,"QUESTION FRAG" )
            addToBackStack(null)
            commit()
        }
    }

    override fun toAnswer(topic: String, your: String, correct: String, numCorrect: String, numTotal:String){
        val allTheData= QuizApp.instance.cryBoi.get()
        val answerFrag = answer.newInstance(allTheData[0].topic, your, correct, numCorrect, numTotal)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere,answerFrag, "ANSWER FRAG")
            addToBackStack(null)
            commit()
        }
    }

    override fun whatNext(topic: String, complete: String) {
        val allTheData= QuizApp.instance.cryBoi.get()
        if(complete.equals("2")) {
            val quizApp = QuizApp.instance
            val crycry = quizApp.cryBoi
            crycry.remove(allTheData[0])
            var intent = Intent(this@all_the_things, MainActivity::class.java)
            startActivity(intent)
            numberCompleted = 0
            numberCorrect = 0
        } else {
            val questionFrag = question.newInstance(allTheData[0].topic ,numberCorrect.toString(), numberCompleted.toString())
            supportFragmentManager.beginTransaction().run {
                //remove from the topic list
                replace(R.id.fragHere,questionFrag, "ANSWER FRAG")
                addToBackStack(null)
                commit()
            }
        }
    }

    fun storeData(topic:String) {
        val quizApp = QuizApp.instance
        val crycry = quizApp.cryBoi
        if (topic.equals("Math")) {
            val Math1 = QuestionDATA("2 + 2", arrayOf("1", "2", "5", "4"), 3)
            val Math2 = QuestionDATA("3 * 42", arrayOf("42", "342", "126", "400"), 2)
            val Math = Topic(
                "Math",
                "There are 2 Questions",
                "This is the math section. You will " +
                        "be doing math. I still barely know my multiplication tables. Don't be like me",
                arrayOf(Math1, Math2)
            )
            crycry.add(Math)
        } else if (topic.equals("Physics")) {
            val Physics1 = QuestionDATA(
                "What is Gravity"  , arrayOf(
                    "Sky Sky Bois", "Weight = Mass * Gravity",
                    "9.8", "Taylor Swift"
                ), 1
            )
            val Physics2 = QuestionDATA(
                "What is inertia? ", arrayOf(
                    "Objects stay in motion",
                    "PEw PEw Bois", "Fast and then slow", "short words"
                ), 0
            )
            val Physics = Topic(
                "Physics", "There are 2 Questions", "Physics is hard. But these" +
                        " questions shouldn't be too bad. Maybe I'm lying though", arrayOf(Physics1, Physics2)
            )
            crycry.add(Physics)
        } else {
            val Marvel1 = QuestionDATA(
                "Is Black Panther a Racist Movie?", arrayOf(
                    "NAH",
                    "YAH", "YOU KNOW IT GURL", "YEET"
                ), 0
            )
            val Marvel2 = QuestionDATA(
                "Is Batman Marvel?", arrayOf(
                    "ARE YOU DUMB?", "NO DUMMY ITS DC", "I don't know",
                    "YES OF COURSE"
                ), 1
            )
            val Marvel = Topic(
                "Marvel Super Heroes",
                "There are 2 Questions",
                "I've only seen " +
                        "like 2 Marvel movies, so these questions are going to suck. That is all",
                arrayOf(Marvel1, Marvel2)
            )
            crycry.add(Marvel)
        }
    }
}