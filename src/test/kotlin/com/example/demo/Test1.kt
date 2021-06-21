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
}
