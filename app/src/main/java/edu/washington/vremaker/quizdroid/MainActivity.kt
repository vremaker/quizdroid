package edu.washington.vremaker.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.*
import kotlinx.android.synthetic.main.topic_overview.*
import org.w3c.dom.Text

var array = arrayOf("Math", "Physics", "Marvel Super Heroes", "Bean Facts", "Your Mom")
var numberCompleted = 0
var numberCorrect = 0
var answerKeep = 0


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ArrayAdapter(this, R.layout.listview_item, array)
        val listView: ListView = findViewById(R.id.listView)
        listView.setAdapter(adapter)
        listView.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // value of item that is clicked
                val itemValue = listView.getItemAtPosition(position) as String
                val intent = Intent(this@MainActivity, TopicOverview::class.java)
                intent.putExtra("topic", itemValue);
                numberCorrect = 0
                numberCompleted = 0
                answerKeep = 0
                startActivity(intent)
            }
        }
    }
}

class TopicOverview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_overview)
        var topic = intent.getStringExtra("topic")
        val topicHead = findViewById<TextView>(R.id.topic)
        topicHead.text = topic
        val descript = findViewById<TextView>(R.id.about)
        val num = findViewById<TextView>(R.id.num)

        if (topic.equals("Math")) {
            descript.text =
                "This is the math section. You will be doing math. I still barely know my multiplication tables. Don't be like me"
            num.text = "There are 2 Questions"
        } else if (topic.equals("Phyics")) {
            descript.text = "Physics is hard. But these questions shouldn't be too bad. Maybe I'm lying though"
            num.text = "There are 2 questions"
        } else if (topic.equals("Marvel Super Heroes")) {
            descript.text = "I've only seen like 2 Marvel movies, so these questions are going to suck. That is all"
            num.text = "There are 2 Questions"
        } else if (topic.equals("Bean Facts")) {
            descript.text =
                "Beans Beans the magical fruit. The more you eat the more you toot. The more you toot the better you feel," +
                        " so answer all these bean questions correctly"
            num.text = "There are 2 Questions"
        } else { /*your mom */
            descript.text =
                "The answer to all of these questions is your mom. If you get any of these wrong, I WILL be upset with you"
            num.text = "There are 2 Questions"
        }

        var select = findViewById<Button>(R.id.begin)
        select.setOnClickListener() {
            val intent = Intent(this@TopicOverview, Question::class.java)
            intent.putExtra("topic", topic);
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@TopicOverview, MainActivity::class.java)
        startActivity(intent)
    }

}


class Question : AppCompatActivity() {
    /** So much hard coding oof */
    var PhysQs = arrayOf("What is weight?", "What is inertia? ")
    var PhysChoices = arrayOf("Sky Sky Bois", "Weight = Mass * Gravity", "9.8", "Taylor Swift", "Object stay in motion", "PEw PEw Bois", "Fast and then slow", "short words")
    var PhysAnsw = arrayOf("Weight = Mass * Gravity", "Fast and then slow")
    var MarvQs = arrayOf("Is Batman Marvel?", "Is Black Panther a Racist Movie?")
    var MarvChoices =  arrayOf("ARE YOU DUMB?", "NO DUMMY ITS DC", "I don't know", "YES OF COURSE", "NAH", "YAH", "YOU KNOW IT GURL", "YEET")
    var MarvAnsw = arrayOf("NO DUMMY ITS DC", "NAH")
    var BeanQs = arrayOf("What type of bean looks like a kidney?", "What bean gives you rainbow skin?")
    var BeanChoices = arrayOf("Kidney Beans", "Pinto Beans", "Eggplant", "Coffee named Jarvis", "Coffee", "Skittles", "Your Mom", "Lima Beans")
    var BeanAnsw = arrayOf("Kidney Beans", "Lima Beans")
    var MomQs = arrayOf("Who is cool?", "Who makes the best dad jokes?")
    var MomChoices = arrayOf("Ted", "Joy", "Your Mom", "Hawk", "Dad", "Your Mom", "TED", "Valerie")
    var MomAnsw = arrayOf("Your Mom", "Your Mom")
    var MathQs = arrayOf("2 + 2", "3 * 42")
    var MathChoices = arrayOf("1", "2", "5", "4", "42", "342", "126", "400")
    var MathAnsw = arrayOf("4", "126")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var topic = intent.getStringExtra("topic")
        setContentView(R.layout.question)
        var question = findViewById<TextView>(R.id.question)
        var radio1 = findViewById<RadioButton>(R.id.answer1)
        var radio2 = findViewById<RadioButton>(R.id.answer2)
        var radio3 = findViewById<RadioButton>(R.id.answer3)
        var radio4 = findViewById<RadioButton>(R.id.answer4)
        var radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        var Qs = arrayOf("")
        var choices = arrayOf("")
        var ans = arrayOf("")
        if (topic.equals("Math")) {
            Qs = MathQs
            choices = MathChoices
            ans = MathAnsw
        } else if (topic.equals("Physics")) {
            Qs = PhysQs
            choices = PhysChoices
            ans = PhysAnsw
        } else if (topic.equals("Marvel Super Heroes")) {
            Qs = MarvQs
            choices = MarvChoices
            ans = MarvAnsw
        } else if (topic.equals("Bean Facts")) {
            Qs = BeanQs
            choices = BeanChoices
            ans = BeanAnsw
        } else {
            Qs = MomQs
            choices = MomChoices
            ans = MomAnsw
        }
        question.text = Qs[numberCompleted]
        radio1.text = choices[answerKeep]
        answerKeep++
        radio2.text = choices[answerKeep]
        answerKeep++
        radio3.text = choices[answerKeep]
        answerKeep++
        radio4.text = choices[answerKeep]
        answerKeep++
        var answer = ans[numberCompleted]
        var submit = findViewById<Button>(R.id.submit)
        submit.setVisibility(View.GONE)
        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                var yourAns = findViewById<RadioButton>(checkedId)
                submit.setVisibility(View.VISIBLE)
                /** set to visible */
                submit.setOnClickListener() {
                    if (yourAns.text.equals(answer)) {
                        numberCorrect++
                    }
                    numberCompleted++
                    var intent = Intent(this@Question, Answer::class.java)
                    intent.putExtra("correct", answer)
                    intent.putExtra("your", yourAns.text); //CHECK FOR RADIO BUTTONS!
                    intent.putExtra("numCorrect", numberCorrect.toString())
                    intent.putExtra("numTot", numberCompleted.toString())
                    intent.putExtra("topic", topic.toString())
                    startActivity(intent)
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@Question, MainActivity::class.java)
        startActivity(intent)
    }
}

class Answer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer_page)
        var nextQuest = findViewById<Button>(R.id.next)
        var corr = findViewById<TextView>(R.id.correct)
        var right = intent.getStringExtra("correct")
        var yourAnsw = intent.getStringExtra("your")
        var numCorr = intent.getStringExtra("numCorrect")
        var total = intent.getStringExtra("numTot")
        var topic = intent.getStringExtra("topic")
        var your = findViewById<TextView>(R.id.you)
        var score = findViewById<TextView>(R.id.score)
        var scoreString = "You have " + numCorr + " out of " + total + " correct!"
        your.text = "Your Answer was " + yourAnsw
        corr.text = "The Correct Answer was " + right
        score.text = scoreString
        if (total.equals("2")) {
            nextQuest.text = "Finish"
            nextQuest.setOnClickListener() {
                val intent = Intent(this@Answer, MainActivity::class.java)
                intent.putExtra("topic", topic)
                startActivity(intent)
            }
        } else {
            nextQuest.setOnClickListener() {
                val intent = Intent(this@Answer, Question::class.java)
                intent.putExtra("topic", topic)
                startActivity(intent)

            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@Answer, MainActivity::class.java)
        startActivity(intent)
    }
}
