package com.example.androidjson

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Login : AppCompatActivity() {
    var JsonId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun Login(view: View) {
        var txtUser = findViewById<EditText>(R.id.loginUser)
        var txtPwd = findViewById<EditText>(R.id.loginPassword)
        var jsonFile = "jsonFile.json"
        var br = BufferedReader(InputStreamReader(openFileInput(jsonFile)))
        var brRead = br.readLine()
        val allText = StringBuilder()
        while (brRead != null) {
            allText.append(brRead + "\n")
            brRead = br.readLine()
            onBackPressed()
        }
        brRead = allText.toString()
        br.close()
        val jsonObject = JSONObject(brRead)
        val jsonArray = jsonObject.optJSONArray("users")
        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.optString("user")
                    .equals(txtUser.text.toString()) and jsonObject.optString("password")
                    .equals(txtPwd.text.toString())
            ) {
                val id = jsonObject.optString("id").toInt()
                JsonId = id
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", JsonId.toString())
                startActivity(intent)
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            }
            if (!jsonObject.optString("user").equals(txtUser.text.toString()) and !jsonObject.optString("password").equals(txtPwd.text.toString())
            ) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun Cancel(view: View) {
        onBackPressed()
    }

}