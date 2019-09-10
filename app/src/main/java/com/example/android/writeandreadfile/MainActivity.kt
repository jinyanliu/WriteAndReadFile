package com.example.android.writeandreadfile

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readAndWriteString()

        readAndWriteObjectPerson()
    }

    private fun readAndWriteString() {
        val fileName = "BusinessCard"
        val fileContent =  "Coffee Cake Carrot Espresso"

        this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }

        val fileInputStream = this.openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var text: String? = null

        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }

        textViewOne.text = stringBuilder.toString()
    }

    private fun readAndWriteObjectPerson() {

        val fileName = "BusinessPerson"
        val p = Person()

        ObjectOutputStream(this.openFileOutput(fileName, Context.MODE_PRIVATE)).use {
            it.writeObject(p)
            it.close()
        }

        ObjectInputStream(this.openFileInput(fileName)).use {
            val result = it.readObject() as? Person
            result?.let { textViewTwo.text = result.name }
            it.close()
        }
    }
}
