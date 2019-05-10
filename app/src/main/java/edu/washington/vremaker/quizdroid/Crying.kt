package edu.washington.vremaker.quizdroid

import java.io.Serializable


class Crying: TopicRepository { //You could say that I am projecting into my code
    val topical = arrayListOf<Topic>()

    override fun get(): ArrayList<Topic> {
        return topical
    }

    override fun add(topic: Topic) {
        this.topical.add(topic)
    }

    override fun remove(topic: Topic) {
        this.topical.remove(topic)
    }

}