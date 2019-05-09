package edu.washington.vremaker.quizdroid

import java.io.Serializable
data class Question(): Serializable
fun class Topic(): Serializable

interface TopicRepository {
    fun storeElements(topic: String): Serializable
}

class thisIsHorrible: TopicRepository {
    override fun storeElements(topic: String): Serializable {
        if(topic.equals("Math")){
            return Topic()
                var MathQs = arrayOf("2 + 2", "3 * 42")
            var MathChoices = arrayOf("1", "2", "5", "4", "42", "342", "126", "400")
            var MathAnsw = arrayOf("4", "126")
            var mathLong = "This is the math section. You will be doing math. I still barely know my multiplication tables. Don't be like me"
            var mathShort = "There are 2 Questions"
        } else if (request.equals("Physics")) {
            var physQ1 = arrayOf("")
            var PhysQs = arrayOf("What is weight?", "What is inertia? ")
            var PhysChoices = arrayOf("Sky Sky Bois", "Weight = Mass * Gravity", "9.8", "Taylor Swift", "Object stay in motion", "PEw PEw Bois", "Fast and then slow", "short words")
            var PhysAnsw = arrayOf("Weight = Mass * Gravity", "Fast and then slow")
            var physLong = "Physics is hard. But these questions shouldn't be too bad. Maybe I'm lying though"
            var physShort = "There are 2 questions"

        } else if (request.equals("Marvel Super Heroes")){
            var MarvQs = arrayOf("Is Batman Marvel?", "Is Black Panther a Racist Movie?")
            var MarvChoices = arrayOf("ARE YOU DUMB?", "NO DUMMY ITS DC", "I don't know", "YES OF COURSE", "NAH", "YAH", "YOU KNOW IT GURL", "YEET")
            var MarvAnsw = arrayOf("NO DUMMY ITS DC", "NAH")
            var marvelLong = "I've only seen like 2 Marvel movies, so these questions are going to suck. That is all"
            var marvelShort = "There are 2 Questions"

            }
    }