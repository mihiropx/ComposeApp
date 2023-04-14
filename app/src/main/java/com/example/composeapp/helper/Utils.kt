package com.example.composeapp.helper

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import org.tartarus.snowball.ext.EnglishStemmer
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern


object Utils {
/*

    private val interpreter: Interpreter = Interpreter(str_to_bb("demo string", Charset.defaultCharset()), null)

    fun isDeviceOnline(context: Context): Boolean {
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
            return if (networkCapabilities == null) {
                Log.d("isDeviceOnline", "false")
                false
            } else {
                Log.d("isDeviceOnline", "true")
                true
            }
        } else {
            // below Marshmallow
            val activeNetwork = connManager.activeNetworkInfo
            return if (activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable) {
                Log.d("isDeviceOnline", "true")
                true
            } else {
                Log.d("isDeviceOnline", "false")
                false
            }
        }
    }

    fun generateEmbedding(text: String): FloatArray {
        // Preprocess the text input
        val inputTensor = interpreter.getInputTensor(0)
        val buffer = preprocessText(text, inputTensor)

        // Generate the text embedding
        val outputTensor = interpreter.getOutputTensor(0)
        val outputBuffer = ByteBuffer.allocateDirect(outputTensor.numBytes())
            .order(ByteOrder.nativeOrder())
        interpreter.run(buffer, outputBuffer)
        val embedding = FloatArray(outputTensor.shape()[1])
        outputBuffer.asFloatBuffer().get(embedding)

        return embedding
    }

    private fun preprocessText(inputText: String, inputTensor: Tensor): List<String> {
        // Convert text to lowercase
        val lowercaseText = inputText.toLowerCase(Locale.ENGLISH)

        // Remove special characters and diacritical marks
        val cleanText = removeSpecialCharacters(lowercaseText)

        // Tokenize the text
        val tokens = tokenizeText(cleanText)

        // Remove stop words
        val filteredTokens = removeStopWords(tokens)

        // Stem the tokens
        val stemmedTokens = stemTokens(filteredTokens)

        // Encode the tokens as input tensor
     //   encodeTokens(stemmedTokens, inputTensor)

        return stemmedTokens
    }

    private fun removeSpecialCharacters(text: String): String {
        val pattern = Pattern.compile("\\p{Punct}|\\d")
        val matcher = pattern.matcher(text)
        val cleanedText = matcher.replaceAll("")
        return Normalizer.normalize(cleanedText, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    private fun tokenizeText(text: String): List<String> {
        // Define the regular expression pattern to split the text into tokens
        val pattern = Pattern.compile("\\s+")

        // Split the text into tokens using the regular expression pattern
        return pattern.split(text).toList()
    }

    private fun removeStopWords(tokens: List<String>): List<String> {
        val stopWords = setOf("the", "and", "a", "an", "in", "on", "at", "for", "with", "is", "are")
        return tokens.filterNot { stopWords.contains(it) }
    }

    private fun stemTokens(tokens: List<String>): List<String> {
        val stemmedTokens = mutableListOf<String>()
        val stemmer = EnglishStemmer()

        for (token in tokens) {
            stemmer.current = token
            if (stemmer.stem()) {
                stemmedTokens.add(stemmer.current)
            } else {
                stemmedTokens.add(token)
            }
        }

        return stemmedTokens
    }

    private fun encodeTokens(tokens: List<String>, inputTensor: Tensor) {
        val numTokens = tokens.size
        val tokenLength = inputTensor.shape()[1]

        val byteBuffer = ByteBuffer.allocateDirect(numTokens * tokenLength * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        for (i in 0 until numTokens) {
            val token = tokens[i]
            val tokenBytes = token.toByteArray(Charsets.UTF_8)
            val tokenLengthDiff = tokenLength - tokenBytes.size

            if (tokenLengthDiff >= 0) {
                byteBuffer.put(tokenBytes)
                for (j in 0 until tokenLengthDiff) {
                    byteBuffer.put(0)
                }
            } else {
                byteBuffer.put(tokenBytes, 0, tokenLength)
            }
        }

//        inputTensor.use {
//            it.write(byteBuffer)
//        }
    }

    private fun str_to_bb(msg: String, charset: Charset?): ByteBuffer {
        return ByteBuffer.wrap(msg.toByteArray(charset!!))
    }
*/

}
