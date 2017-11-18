package me.ngima.lambda

import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern

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
    fun selct() {

        val list = listOf(1, 1)
        list.filter { it % 2 == 0 }
                .forEach(Logger::log)
    }

    fun getHahaImo(hahaString: String): String {
        val pattern = Pattern.compile("ha")
        val matcher = pattern.matcher(hahaString)

        var haha = ""
        while (matcher.find()) haha += ":D "
        return haha
    }
}
