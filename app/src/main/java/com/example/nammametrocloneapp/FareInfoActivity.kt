package com.example.nammametrocloneapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class FareInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fare_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val etFromStation = findViewById<EditText>(R.id.etFromStation)
        val etToStation = findViewById<EditText>(R.id.etToStation)
        val btnCheckFare = findViewById<MaterialButton>(R.id.btnCheckFare)

        val fromStationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val station = result.data?.getStringExtra(StationSelectionActivity.EXTRA_SELECTED_STATION)
                etFromStation.setText(station)
            }
        }

        val toStationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val station = result.data?.getStringExtra(StationSelectionActivity.EXTRA_SELECTED_STATION)
                etToStation.setText(station)
            }
        }

        etFromStation.setOnClickListener {
            val intent = Intent(this, StationSelectionActivity::class.java)
            intent.putExtra(StationSelectionActivity.EXTRA_IS_FROM_STATION, true)
            fromStationLauncher.launch(intent)
        }

        etToStation.setOnClickListener {
            val intent = Intent(this, StationSelectionActivity::class.java)
            intent.putExtra(StationSelectionActivity.EXTRA_IS_FROM_STATION, false)
            toStationLauncher.launch(intent)
        }

        btnCheckFare.setOnClickListener {
            val fromStation = etFromStation.text.toString()
            val toStation = etToStation.text.toString()
            
            if (fromStation.isBlank() || toStation.isBlank()) {
                Toast.makeText(this, "Please select both stations", Toast.LENGTH_SHORT).show()
            } else if (fromStation.equals(toStation, ignoreCase = true)) {
                Toast.makeText(this, "Source and destination cannot be the same", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fare is ₹ 45", Toast.LENGTH_LONG).show()
            }
        }
    }
}
