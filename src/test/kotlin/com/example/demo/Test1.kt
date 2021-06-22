package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.google.gson.Gson
import org.junit.jupiter.api.Test

class Test1 {
    val json = """
               {
                  "words":["word1", "word2"],
                  "pass":"1234",
                  "field1": {
                    "field2": "val"
                  }
               }
        """

    @Test
    fun aaa() {
        val objectNode = ObjectMapper().readTree(json) as ObjectNode
        val jsonNode = objectNode["words"]
        (objectNode["words"] as ArrayNode).add("123")
        objectNode.put("pass", "qwerty")
        (objectNode["field1"] as ObjectNode).put("field2", "psss...")
        println(objectNode)
    }

    @Test
    fun bbb() {
        val a = Gson().fromJson(json, Any::class.java)
        println(a)
    }

    operator fun ObjectNode.get(key: String?): ObjectNode {
        return this.get(key) as ObjectNode
    }

    @Test
    fun ccc() {
        mapOf(Aaaa.AAA to Numb(1), Aaaa.BBB to Numb(2))
            .map { "${it.key}:\n${it.value}" }
            .joinToString("\n-------------\n")
            .let { println(it) }

        println("----------//----------")

        mapOf(Aaaa.AAA to Numb(1), Aaaa.BBB to Numb(2))
            .reduce("") { entry, acc -> "$acc${entry.key}:\n${entry.value}\n-------------\n" }
            .let { println(it) }
    }
}

private fun <K, V, R> Map<K, V>.reduce(acc: R, operation: (entry: Map.Entry<K, V>, acc: R) -> R): R =
    iterator().run {
        when {
            !hasNext() -> acc
            else -> {
                var accumulator = acc
                while (hasNext()) accumulator = operation(next(), accumulator)
                accumulator
            }
        }
    }

fun <K, V> Map.Entry<K, V>.toString(separator: String) = "$key$separator$value"

enum class Aaaa {
    AAA,
    BBB
}

data class Numb(val i: Int) {
    override fun toString(): String = "numb: i"
}
