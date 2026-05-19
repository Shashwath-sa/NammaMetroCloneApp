package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class PurchaseTicketActivity : AppCompatActivity() {

    private var passengerCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_purchase_ticket)

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
        val btnSwap = findViewById<ImageView>(R.id.btnSwap)
        
        val tvPassengerCount = findViewById<TextView>(R.id.tvPassengerCount)
        val btnAddPassenger = findViewById<ImageView>(R.id.btnAddPassenger)
        val btnRemovePassenger = findViewById<ImageView>(R.id.btnRemovePassenger)
        val btnPurchaseConfirm = findViewById<MaterialButton>(R.id.btnPurchaseConfirm)

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

        btnSwap.setOnClickListener {
            val fromText = etFromStation.text.toString()
            val toText = etToStation.text.toString()
            etFromStation.setText(toText)
            etToStation.setText(fromText)
        }

        btnAddPassenger.setOnClickListener {
            if (passengerCount < 6) {
                passengerCount++
                tvPassengerCount.text = passengerCount.toString()
            } else {
                Toast.makeText(this, "Maximum 6 passengers allowed", Toast.LENGTH_SHORT).show()
            }
        }

        btnRemovePassenger.setOnClickListener {
            if (passengerCount > 1) {
                passengerCount--
                tvPassengerCount.text = passengerCount.toString()
            }
        }

        btnPurchaseConfirm.setOnClickListener {
            val fromStation = etFromStation.text.toString()
            val toStation = etToStation.text.toString()
            
            if (fromStation.isBlank() || toStation.isBlank()) {
                Toast.makeText(this, "Please select both stations", Toast.LENGTH_SHORT).show()
            } else if (fromStation.equals(toStation, ignoreCase = true)) {
                Toast.makeText(this, "Source and destination cannot be the same", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Proceeding to payment for \$passengerCount ticket(s)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
