package com.example.androidjson


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Info : AppCompatActivity() {
    var jsonFile = "jsonFile.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }

    override fun onStart() {
        val objetoIntent: Intent = intent
        var id = objetoIntent.getStringExtra("id")
        var infoName = findViewById<EditText>(R.id.txtRegistryName)
        var infoSurname = findViewById<EditText>(R.id.txtInfoSurname)
        var loginUser = findViewById<EditText>(R.id.loginUser)
        var loginPwd = findViewById<EditText>(R.id.loginPassword)
        var br = BufferedReader(InputStreamReader(openFileInput(jsonFile)))
        var brRead = br.readLine()
        val readAll = StringBuilder()
        while (brRead != null) {
            readAll.append(brRead + "\n")
            brRead = br.readLine()
        }
        brRead = readAll.toString()
        br.close()
        val jsonObject = JSONObject(brRead)
        val jsonArray = jsonObject.optJSONArray("users")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.optString("id").equals(id)) {
                val id = jsonObject.optString("id").toInt()
                val name = jsonObject.optString("name")
                val surname = jsonObject.optString("surname")
                val user = jsonObject.optString("user")
                val password = jsonObject.optString("password")
                infoName.setText(name)
                infoSurname.setText(surname)
                loginUser.setText(user)
                loginPwd.setText(password)
            }
        }
        super.onStart()
    }

    fun Update(view: View) {
        var num = 0
        val intent: Intent = intent
        var id = intent.getStringExtra("id")!!.toInt()
        var infoName = findViewById<EditText>(R.id.txtRegistryName)
        var infoSurname = findViewById<EditText>(R.id.txtInfoSurname)
        var loginUser = findViewById<EditText>(R.id.loginUser)
        var loginPwd = findViewById<EditText>(R.id.loginPassword)
        num = id
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(jsonFile)))
        var textReaded = bufferedReader.readLine()
        val todo = StringBuilder()
        while (textReaded != null) {
            todo.append(textReaded + "\n")
            textReaded = bufferedReader.readLine()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }
        textReaded = todo.toString()
        bufferedReader.close()
        val jsonObject = JSONObject(textReaded)
        val jsonArray = jsonObject.optJSONArray("users")
        jsonArray.remove(id)
        val main = JSONObject()
        main.put("id", num)
        main.put("name", infoName.text)
        main.put("surname", infoSurname.text)
        main.put("user", loginUser.text)
        main.put("password", loginPwd.text)
        jsonArray.put(main)
        var fileOutput = openFileOutput(jsonFile, Context.MODE_PRIVATE)
        fileOutput.write(jsonObject.toString().toByteArray())
        fileOutput.close()
    }

    fun Cancel(view: View) {
        onBackPressed()
    }
}
