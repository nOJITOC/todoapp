package com.mmteams91.todoapp

import com.mmteams91.todoapp.common.extensions.fromJson
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val moshi = Moshi.Builder().build()
        val string = "{\"a\" = 1 , \"b\" = 2.0}"
        val map = moshi.fromJson<Map<String,Any?>>(string)
        println()

    }
}
