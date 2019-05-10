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
            val allTheData= QuizApp.instance.cryBoi.get()
            val topic = it.getString("topic")
            val topicHead = rootView.findViewById<TextView>(R.id.topic)
            topicHead.text = topic //COME BACK AND FIX WITH ACTUAL TOPIC
            val descript = rootView.findViewById<TextView>(R.id.about)
            val num = rootView.findViewById<TextView>(R.id.num)
            descript.text = allTheData[0].long
            num.text = allTheData[0].short
            val button = rootView.findViewById<Button>(R.id.begin)
            button.setOnClickListener(){
                Log.e("BUTTON PRESS", topic)
                callback!!.toQuestion(topic)
            }
        }
        return rootView
    }
}
