package com.devsparkle.twitterclient.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileReader {


    @Throws(IOException::class)
    fun readTestFile(jsonFileName: String): String {
        val inputStream = this::class.java
            .getResourceAsStream("/assets/$jsonFileName") ?: this::class.java
            .getResourceAsStream("/$jsonFileName")
        ?: throw NullPointerException(
            "Have you added the local resource correctly?, "
                    + "Hint: name it as: " + jsonFileName
        )
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }
}