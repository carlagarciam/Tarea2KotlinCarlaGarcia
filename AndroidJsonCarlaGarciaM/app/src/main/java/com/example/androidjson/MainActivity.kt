package com.example.androidjson

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    var id = "0"
    var jsonFile = "jsonFile.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = intent
        id = intent.getStringExtra("id").toString()
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var btnInformacion = findViewById<Button>(R.id.btnInfo)
        btnInformacion.setEnabled(false)
        btnLogin.setEnabled(false)
        fileExists()
    }


    override fun onStart() {
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(jsonFile)))
        var brRead = bufferedReader.readLine()
        val readAll = StringBuilder()
        while (brRead != null) {
            readAll.append(brRead + "\n")
            brRead = bufferedReader.readLine()
        }
        bufferedReader.close()
        brRead = readAll.toString()
        val jsonObject = JSONObject(brRead)
        val jsonArray = jsonObject.optJSONArray("users")
        if (!jsonArray.toString().equals("[]")) {
            btnLogin.setEnabled(true)
        }
        var btnInformacion = findViewById<Button>(R.id.btnInfo)
        if (id != "null") {
            btnInformacion.setEnabled(true)
        }
        super.onStart()
    }

    fun goInfo(view: View) {
        val intent: Intent = intent
        var startIntent = Intent(this, Info::class.java)
        id = intent.getStringExtra("id").toString()
        startIntent.putExtra("id", id)
        startActivity(startIntent)
    }

    fun goLogin(view: View) {
        var startIntent = Intent(this, Login::class.java)
        startActivity(startIntent)
    }

    fun goRegistry(view: View) {
        var startIntent = Intent(this, Registry::class.java)
        startActivity(startIntent)
    }

    fun fileExists() {
        var empty = "{\"users\": []}"
        var file = File(getFilesDir().getAbsolutePath(), jsonFile)
        if (!file.exists()) {
            var fileOutput = openFileOutput(jsonFile, Context.MODE_PRIVATE)
            fileOutput.write(empty.toByteArray())
            fileOutput.close()
        }
    }
}