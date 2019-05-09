package edu.washington.vremaker.quizdroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_answer.*


class answer : Fragment() {

    private var callback: whatNextListener? = null

    interface whatNextListener {
        fun whatNext(topic:String, numberCompleted: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? whatNextListener
        if (callback == null) {
            throw ClassCastException("$context must implement answerListener")
        }
    }

    companion object {
        fun newInstance(topic: String, your: String, correct: String, numCorrect: String, numTot: String): answer {
            val args = Bundle().apply {
                putString("topic", topic)
                putString("your", your)
                putString("correct", correct)
                putString("numCorrect", numCorrect)
                putString("numTotal", numTot)
            }

            val fragment = answer().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_answer, container, false)

        arguments?.let {
            //Params passed in
            val yourAnsw = it.getString("your")
            val topic = it.getString("topic")
            val numTot = it.getString("numTotal")
            val numCorrect = it.getString("numCorrect")
            val correct = it.getString("correct")
            //Dom elements
            var nextQuest = rootView.findViewById<Button>(R.id.next)
            var corr = rootView.findViewById<TextView>(R.id.correct)
            var your = rootView.findViewById<TextView>(R.id.you)
            var score = rootView.findViewById<TextView>(R.id.score)

            //Dom manipulation
            var scoreString = "You have " + numCorrect + " out of " + numTot + " correct!"
            your.text = "Your Answer was " + yourAnsw
            corr.text = "The Correct Answer was " + correct
            score.text = scoreString
            if (numTot.equals("2")) {
                nextQuest.text = "Finish"
            }
            nextQuest.setOnClickListener() {
                callback!!.whatNext(topic, numberCompleted.toString())
            }
        }

        return rootView
    }

}
