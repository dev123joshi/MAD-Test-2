package com.example.travelpass

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WelcomeActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var tvProfileDetails: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Connect TextViews
        tvWelcome = findViewById(R.id.tvWelcome)
        tvProfileDetails = findViewById(R.id.tvProfileDetails)

        // SharedPreferences
        sharedPreferences =
            getSharedPreferences("TravelPassPrefs", MODE_PRIVATE)

        // Get saved profile information
        val name =
            sharedPreferences.getString("name", "Traveller")

        val distance =
            sharedPreferences.getString("distance", "Kilometers")

        val temperature =
            sharedPreferences.getString("temperature", "Celsius")

        val alerts =
            sharedPreferences.getBoolean("alerts", false)

        // Welcome message
        tvWelcome.text = "Welcome, $name! 👋"

        // Display profile details
        tvProfileDetails.text =
            "Distance Unit: $distance\n\n" +
                    "Temperature: $temperature\n\n" +
                    "Travel Alerts: $alerts"
    }

    // Create the Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle Menu clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menuEditProfile -> {
                startActivity(
                    Intent(this, ProfileActivity::class.java)
                )
                return true
            }

            R.id.menuSettings -> {
                Toast.makeText(
                    this,
                    "Settings Selected",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }

            R.id.menuLogout -> {

                // Clear saved login information
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                // Return to Login Screen
                val intent =
                    Intent(this, MainActivity::class.java)

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}