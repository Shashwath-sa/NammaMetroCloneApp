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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.max
import kotlin.math.min

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

        val routeDistances = listOf(
            "Whitefield (Kadugodi)" to 1.5,
            "Hopefarm Channasandra" to 1.0,
            "Kadugodi Tree Park" to 1.2,
            "Pattandur Agrahara" to 1.1,
            "Sri Sathya Sai Hospital" to 1.0,
            "Nallurhalli" to 1.3,
            "Kundalahalli" to 1.1,
            "Seetharam Palya" to 1.4,
            "Hoodi" to 1.0,
            "Garudacharpalya" to 1.0,
            "Singayyanapalya" to 1.4,
            "K.R. Pura" to 1.6,
            "Benniganahalli" to 1.8,
            "Baiyappanahalli" to 1.2,
            "Swami Vivekananda Road" to 1.0,
            "Indiranagar" to 1.2,
            "Halasuru" to 1.0,
            "Trinity" to 1.0,
            "M.G. Road" to 1.0,
            "Cubbon Park" to 0.9,
            "Vidhana Soudha" to 1.0,
            "Central College" to 1.1,
            "Majestic" to 0.8,
            "City Railway Station" to 1.0,
            "Magadi Road" to 1.2,
            "Hosahalli" to 1.1,
            "Vijayanagar" to 1.0,
            "Attiguppe" to 1.1,
            "Deepanjali Nagar" to 1.2,
            "Mysuru Road" to 1.0,
            "Pantharapalya" to 1.3,
            "Nayandahalli" to 1.5,
            "Rajarajeshwari Nagar" to 1.2,
            "Jnanabharathi" to 1.4,
            "Pattanagere" to 1.1,
            "Kengeri Bus Terminal" to 1.0,
            "Kengeri" to 2.1,
            "Challaghatta" to 0.0
        )

        fun getMappedStationName(station: String): String {
            return when (station) {
                "Dr. B.R. Ambedkar (Vidhana Soudha)" -> "Vidhana Soudha"
                "Sir M. Visvesvaraya (Central College)" -> "Central College"
                "Nadaprabhu Kempegowda (Majestic)" -> "Majestic"
                "Mysore Road" -> "Mysuru Road"
                "KR Puram" -> "K.R. Pura"
                "Seetharama Palya" -> "Seetharam Palya"
                else -> station
            }
        }

        btnCheckFare.setOnClickListener {
            val fromStation = etFromStation.text.toString()
            val toStation = etToStation.text.toString()
            
            if (fromStation.isBlank() || toStation.isBlank()) {
                Toast.makeText(this, "Please select both stations", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } 
            
            if (fromStation.equals(toStation, ignoreCase = true)) {
                Toast.makeText(this, "Source and destination cannot be the same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mappedFrom = getMappedStationName(fromStation)
            val mappedTo = getMappedStationName(toStation)

            val fromIndex = routeDistances.indexOfFirst { it.first == mappedFrom }
            val toIndex = routeDistances.indexOfFirst { it.first == mappedTo }

            var distance = 0.0
            if (fromIndex != -1 && toIndex != -1) {
                val start = min(fromIndex, toIndex)
                val end = max(fromIndex, toIndex)
                for (i in start until end) {
                    distance += routeDistances[i].second
                }
            } else {
                distance = 10.0 // Default fallback distance if stations not on this line
            }

            // Round distance to 1 decimal place
            distance = Math.round(distance * 10.0) / 10.0

            val tokenFare = when {
                distance <= 2.0 -> 10
                distance <= 4.0 -> 20
                distance <= 6.0 -> 30
                distance <= 8.0 -> 40
                distance <= 10.0 -> 50
                distance <= 15.0 -> 60
                distance <= 20.0 -> 70
                distance <= 25.0 -> 80
                else -> 90
            }

            val smartCardFare = tokenFare * 0.95

            val message = "Distance: $distance km\n\n" +
                          "Token Fare: ₹$tokenFare\n" +
                          "Smart Card Fare*: ₹${String.format("%.2f", smartCardFare)}"

            MaterialAlertDialogBuilder(this)
                .setTitle("Fare Info")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
