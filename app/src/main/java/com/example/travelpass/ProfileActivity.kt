package com.example.travelpass

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var rgDistance: RadioGroup
    private lateinit var rgTemperature: RadioGroup
    private lateinit var cbAlerts: CheckBox
    private lateinit var btnSaveProfile: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        etName = findViewById(R.id.etName)
        rgDistance = findViewById(R.id.rgDistance)
        rgTemperature = findViewById(R.id.rgTemperature)
        cbAlerts = findViewById(R.id.cbAlerts)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)

        sharedPreferences =
            getSharedPreferences("TravelPassPrefs", MODE_PRIVATE)

        // Get username from Login screen
        val username = intent.getStringExtra("username")

        // Display username as default name
        etName.setText(username)

        btnSaveProfile.setOnClickListener {

            val name = etName.text.toString().trim()

            if (name.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter your name",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val selectedDistanceId =
                    rgDistance.checkedRadioButtonId

                val selectedTemperatureId =
                    rgTemperature.checkedRadioButtonId

                val distanceRadio =
                    findViewById<RadioButton>(selectedDistanceId)

                val temperatureRadio =
                    findViewById<RadioButton>(selectedTemperatureId)

                val distance =
                    distanceRadio.text.toString()

                val temperature =
                    temperatureRadio.text.toString()

                // Save profile information
                val editor = sharedPreferences.edit()

                editor.putString("name", name)
                editor.putString("distance", distance)
                editor.putString("temperature", temperature)
                editor.putBoolean(
                    "alerts",
                    cbAlerts.isChecked
                )

                editor.apply()

                // Go to Welcome screen
                val intent =
                    Intent(this, WelcomeActivity::class.java)

                intent.putExtra("name", name)

                startActivity(intent)
            }
        }
    }
}