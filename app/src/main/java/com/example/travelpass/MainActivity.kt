package com.example.travelpass

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Open SharedPreferences
        val prefs = getSharedPreferences("TravelPassPrefs", MODE_PRIVATE)

        // -----------------------------
        // LOAD SAVED DATA WHEN APP OPENS
        // -----------------------------
        val remembered = prefs.getBoolean("rememberMe", false)

        if (remembered) {
            val savedUsername = prefs.getString("username", "") ?: ""

            etUsername.setText(savedUsername)
            cbRememberMe.isChecked = true
        }

        // -----------------------------
        // SAVE DATA WHEN LOGIN IS CLICKED
        // -----------------------------
        btnLogin.setOnClickListener {

            val username = etUsername.text.toString().trim()

            if (cbRememberMe.isChecked && username.isNotEmpty()) {

                prefs.edit()
                    .putBoolean("rememberMe", true)
                    .putString("username", username)
                    .commit()   // commit immediately

            } else {

                prefs.edit()
                    .remove("rememberMe")
                    .remove("username")
                    .commit()
            }

            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }
}