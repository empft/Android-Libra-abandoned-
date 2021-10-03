package com.example.libraandroid

import com.example.libraandroid.service.session.SessionToken
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.assertEquals
import org.junit.Test

@Serializable
data class SessionToken(
    val name: String,
    val token: String
)

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun jsonList_isWorking() {
        val all = mutableListOf(SessionToken("myName", "secret"), SessionToken("another", "not so secret"))

        val temp = all.toList()
        val encoded = Json.encodeToJsonElement(temp)
        val test = Json.decodeFromJsonElement<MutableList<SessionToken>>(encoded)
        assertEquals("Successful serialization and deserialization", test, all)
    }
}

