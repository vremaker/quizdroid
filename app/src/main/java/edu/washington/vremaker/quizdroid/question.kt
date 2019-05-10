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


    private var callback: answerListener? = null


    interface answerListener{
        fun toAnswer(topic: String, your: String, correct: String, numCorrect: String, numTotal:String)
    }

    companion object {
        fun newInstance(topic: String, numCorrect: String, numberComplete: String): question {
            val args = Bundle().apply {
                putString("topic", topic)
                Log.e("OBJECT", topic)
                putString("numCorrect", numCorrect)
                putString("numberComplete", numberComplete)
                putString("numberComplete", numberComplete)
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
            val allTheData: QuestionDATA
            var question = rootView.findViewById<TextView>(R.id.question)
            var radio1 = rootView.findViewById<RadioButton>(R.id.answer1)
            var radio2 = rootView.findViewById<RadioButton>(R.id.answer2)
            var radio3 = rootView.findViewById<RadioButton>(R.id.answer3)
            var radio4 = rootView.findViewById<RadioButton>(R.id.answer4)
            var radioGroup = rootView.findViewById<RadioGroup>(R.id.radioGroup)
            if(numberCompleted === 0) {
                allTheData= QuizApp.instance.cryBoi.get()[0].questions[0]
            } else {
                allTheData= QuizApp.instance.cryBoi.get()[0].questions[1]
            }
            question.text = allTheData.question
            radio1.text = allTheData.choices[0]
            radio2.text = allTheData.choices[1]
            radio3.text = allTheData.choices[2]
            radio4.text = allTheData.choices[3]
            var submit = rootView.findViewById<Button>(R.id.submit)
            submit.setVisibility(View.GONE)
            radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    var yourAns = rootView.findViewById<RadioButton>(checkedId)
                    submit.setVisibility(View.VISIBLE)

                    submit.setOnClickListener() {
                        if (yourAns.text.equals(allTheData.choices[allTheData.correct])) {
                            numberCorrect++
                        }
                        numberCompleted++
                        callback!!.toAnswer(QuizApp.instance.cryBoi.get()[0].topic, yourAns.text.toString(),
                            allTheData.choices[allTheData.correct], numberCorrect.toString(), numberCompleted.toString())
                    }
                })
        }
        return rootView
    }
}