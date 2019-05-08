package edu.washington.vremaker.quizdroid


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*


class topic_overview : Fragment() {

    private var callback: startListener? = null
     interface startListener {
        fun toQuestion(topic: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? startListener
        if (callback == null) {
            throw ClassCastException("$context must implement startListener")
        }
    }

    companion object {
        fun newInstance(topic: String): topic_overview {
            val args = Bundle().apply {
                putString("topic", topic)
            }

            val fragment = topic_overview().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_topic_overview, container, false)
        arguments?.let {
            val topic = it.getString("topic")
            val topicHead = rootView.findViewById<TextView>(R.id.topic)
            topicHead.text = topic //COME BACK AND FIX WITH ACTUAL TOPIC
            val descript = rootView.findViewById<TextView>(R.id.about)
            val num = rootView.findViewById<TextView>(R.id.num)
            Log.e("REEEE TOPIOC", topic)
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
            val button = rootView.findViewById<Button>(R.id.begin)
            button.setOnClickListener(){
                Log.e("BUTTON PRESS", topic)
                callback!!.toQuestion(topic)
            }
        }
        return rootView
    }
}
