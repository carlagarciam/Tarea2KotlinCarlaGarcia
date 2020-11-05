package com.example.androidjson

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Registry : AppCompatActivity() {
    var num = 0
    var jsonFile = "jsonFile.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
    }
    fun Anadir(view: View) {
        var name = findViewById<EditText>(R.id.txtRegistryName)
        var surname = findViewById<EditText>(R.id.txtInfoSurname)
        var user = findViewById<EditText>(R.id.loginUser)
        var password = findViewById<EditText>(R.id.loginPassword)
        var br = BufferedReader(InputStreamReader(openFileInput(jsonFile)))
        var brRead = br.readLine()
        val readAll = StringBuilder()
        while (brRead != null) {
            readAll.append(brRead + "\n")
            brRead = br.readLine()
            Toast.makeText(this, "Usuario Añadido", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        brRead = readAll.toString()
        val jsonObject = JSONObject(brRead)
        val jsonArray = jsonObject.optJSONArray("users")
        if (jsonArray.length() == 0) {
            val jsonObject = JSONObject(brRead)
            val jsonArray = jsonObject.optJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                num = i + 1
            }
            val main = JSONObject()
            main.put("id", num)
            main.put("name", name.text)
            main.put("surname", surname.text)
            main.put("user", user.text)
            main.put("password", password.text)
            jsonArray.put(main)
            var fileOutput = openFileOutput(jsonFile, Context.MODE_PRIVATE)
            fileOutput.write(jsonObject.toString().toByteArray())
            fileOutput.close()
        }
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (!jsonObject.optString("user").equals(user.text.toString())) {

                val jsonObject = JSONObject(brRead)
                val jsonArray = jsonObject.optJSONArray("users")
                for (i in 0 until jsonArray.length()) {
                    num = i + 1
                }
                val main = JSONObject()
                main.put("id", num)
                main.put("name", name.text)
                main.put("surname", surname.text)
                main.put("user", user.text)
                main.put("password", password.text)
                jsonArray.put(main)
                var fileOutput = openFileOutput(jsonFile, Context.MODE_PRIVATE)
                fileOutput.write(jsonObject.toString().toByteArray())
                fileOutput.close()

            }
        }
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.optString("user").equals(user.text.toString())) {
                val id = jsonObject.optString("id").toInt()
                val jsonObject = JSONObject(brRead)
                val jsonArray = jsonObject.optJSONArray("users")
                jsonArray.remove(id)
                val main = JSONObject()
                main.put("id", num)
                main.put("name", name.text)
                main.put("surname", surname.text)
                main.put("user", user.text)
                main.put("password", password.text)
                jsonArray.put(main)
                var fileOutput = openFileOutput(jsonFile, Context.MODE_PRIVATE)
                fileOutput.write(jsonObject.toString().toByteArray())
                fileOutput.close()
            }
        }


    }
    fun Cancel(view: View) {
        onBackPressed()
    }


}