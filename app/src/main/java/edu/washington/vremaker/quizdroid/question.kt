package edu.washington.vremaker.quizdroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.util.Log


class question : Fragment() {

    var PhysQs = arrayOf("What is weight?", "What is inertia? ")
    var PhysChoices = arrayOf(
        "Sky Sky Bois",
        "Weight = Mass * Gravity",
        "9.8",
        "Taylor Swift",
        "Object stay in motion",
        "PEw PEw Bois",
        "Fast and then slow",
        "short words"
    )
    var PhysAnsw = arrayOf("Weight = Mass * Gravity", "Object stay in motion")
    var MarvQs = arrayOf("Is Batman Marvel?", "Is Black Panther a Racist Movie?")
    var MarvChoices = arrayOf(
        "ARE YOU DUMB?",
        "NO DUMMY ITS DC",
        "I don't know",
        "YES OF COURSE",
        "NAH",
        "YAH",
        "YOU KNOW IT GURL",
        "YEET"
    )
    var MarvAnsw = arrayOf("NO DUMMY ITS DC", "NAH")
    var BeanQs = arrayOf("What type of bean looks like a kidney?", "What bean gives you rainbow skin?")
    var BeanChoices = arrayOf(
        "Kidney Beans",
        "Pinto Beans",
        "Eggplant",
        "Coffee named Jarvis",
        "Coffee",
        "Skittles",
        "Your Mom",
        "Lima Beans"
    )
    var BeanAnsw = arrayOf("Kidney Beans", "Lima Beans")
    var MomQs = arrayOf("Who is cool?", "Who makes the best dad jokes?")
    var MomChoices = arrayOf("Ted", "Joy", "Your Mom", "Hawk", "Dad", "Your Mom", "TED", "Valerie")
    var MomAnsw = arrayOf("Your Mom", "Your Mom")
    var MathQs = arrayOf("2 + 2", "3 * 42")
    var MathChoices = arrayOf("1", "2", "5", "4", "42", "342", "126", "400")
    var MathAnsw = arrayOf("4", "126")

    private var callback: answerListener? = null


    interface answerListener{
        fun toAnswer(topic: String, your: String, correct: String, numCorrect: String, numTotal:String)
    }

    companion object {
        fun newInstance(topic: String, numCorrect: String, numberComplete: String, answerKeep: String): question {
            val args = Bundle().apply {
                putString("topic", topic)
                Log.e("OBJECT", topic)
                putString("numCorrect", numCorrect)
                putString("numberComplete", numberComplete)
                putString("answerKeep", answerKeep)
            }

            val fragment = question().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as? answerListener
        if (callback == null) {
            throw ClassCastException("$context must implement answerListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_question, container, false)

        arguments?.let {
            val topic = it.getString("topic")
            var question = rootView.findViewById<TextView>(R.id.question)
            var radio1 = rootView.findViewById<RadioButton>(R.id.answer1)
            var radio2 = rootView.findViewById<RadioButton>(R.id.answer2)
            var radio3 = rootView.findViewById<RadioButton>(R.id.answer3)
            var radio4 = rootView.findViewById<RadioButton>(R.id.answer4)
            var radioGroup = rootView.findViewById<RadioGroup>(R.id.radioGroup)
            var Qs = arrayOf("")
            var choices = arrayOf("")
            var ans = arrayOf("")
            Log.e("fuck", topic)
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
            var submit = rootView.findViewById<Button>(R.id.submit)
            submit.setVisibility(View.GONE)
            radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    var yourAns = rootView.findViewById<RadioButton>(checkedId)
                    submit.setVisibility(View.VISIBLE)

                    submit.setOnClickListener() {
                        if (yourAns.text.equals(answer)) {
                            numberCorrect++
                        }
                        numberCompleted++
                        callback!!.toAnswer(topic, yourAns.text.toString(), answer, numberCorrect.toString(), numberCompleted.toString())
                    }
                })
        }
        return rootView
    }
}
