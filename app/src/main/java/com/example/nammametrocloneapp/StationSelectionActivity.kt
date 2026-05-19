package com.example.nammametrocloneapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StationSelectionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IS_FROM_STATION = "extra_is_from_station"
        const val EXTRA_SELECTED_STATION = "extra_selected_station"
    }

    private val stations = listOf(
        "Attiguppe",
        "Baiyappanahalli",
        "Banashankari",
        "Bommanahalli",
        "Bommasandra",
        "BTM Layout",
        "Central Silk Board",
        "Challaghatta",
        "Chickpete",
        "City Railway Station",
        "Cubbon Park",
        "Dasarahalli",
        "Deepanjali Nagar",
        "Doddakallasandra",
        "Dr. B.R. Ambedkar (Vidhana Soudha)",
        "Electronic City",
        "Goraguntepalya",
        "Halasuru",
        "Hopefarm Channasandra",
        "Hosahalli",
        "Indiranagar",
        "Jalahalli",
        "Jayadeva Hospital",
        "Jayanagar",
        "Jnanabharathi",
        "JP Nagar",
        "Kadugodi Tree Park",
        "Kengeri",
        "Kengeri Bus Terminal",
        "Konanakunte Cross",
        "KR Market",
        "KR Puram",
        "Kundalahalli",
        "Kuvempu Road",
        "Lalbagh",
        "Madavara",
        "Magadi Road",
        "Mahalakshmi",
        "MG Road",
        "Mysore Road",
        "Nadaprabhu Kempegowda (Majestic)",
        "Nagasandra",
        "Nallurhalli",
        "National College",
        "Nayandahalli",
        "Pattanagere",
        "Pattandur Agrahara",
        "Peenya",
        "Peenya Industry",
        "Rajajinagar",
        "Rajarajeshwari Nagar",
        "RV Road",
        "Sampige Road",
        "Sandal Soap Factory",
        "Seetharama Palya",
        "Silk Institute",
        "Sir M. Visvesvaraya (Central College)",
        "South End Circle",
        "Sri Sathya Sai Hospital",
        "Srirampura",
        "Swami Vivekananda Road",
        "Thalaghattapura",
        "Trinity",
        "Vajarahalli",
        "Vijayanagar",
        "Whitefield (Kadugodi)",
        "Yelachenahalli",
        "Yeshwanthpur"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_station_selection)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val isFromStation = intent.getBooleanExtra(EXTRA_IS_FROM_STATION, true)
        
        val etSearchStation = findViewById<EditText>(R.id.etSearchStation)
        if (isFromStation) {
            etSearchStation.hint = getString(R.string.hint_enter_from_station)
        } else {
            etSearchStation.hint = getString(R.string.hint_enter_to_station)
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StationAdapter(stations) { selectedStation ->
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SELECTED_STATION, selectedStation)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
