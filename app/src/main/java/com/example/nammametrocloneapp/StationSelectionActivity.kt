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
        "Attiguppe", "Baiyappanahalli", "Banashankari", "Benniganahalli", "Bommanahalli",
        "Bommasandra", "BTM Layout", "Central Silk Board", "Challaghatta", "Chickpete",
        "City Railway Station", "Cubbon Park", "Dasarahalli", "Deepanjali Nagar",
        "Doddakallasandra", "Dr. B.R. Ambedkar (Vidhana Soudha)", "Electronic City",
        "Garudacharpalya", "Goraguntepalya", "Halasuru", "Hoodi", "Hopefarm Channasandra",
        "Hosahalli", "Indiranagar", "Jalahalli", "Jayadeva Hospital", "Jayanagar",
        "Jnanabharathi", "JP Nagar", "Kadugodi Tree Park", "Kengeri", "Kengeri Bus Terminal",
        "Konanakunte Cross", "KR Market", "KR Puram", "Kundalahalli", "Kuvempu Road",
        "Lalbagh", "Madavara", "Magadi Road", "Mahalakshmi", "MG Road", "Mysore Road",
        "Nadaprabhu Kempegowda (Majestic)", "Nagasandra", "Nallurhalli", "National College",
        "Nayandahalli", "Pantharapalya", "Pattanagere", "Pattandur Agrahara", "Peenya",
        "Peenya Industry", "Rajajinagar", "Rajarajeshwari Nagar", "RV Road", "Sampige Road",
        "Sandal Soap Factory", "Seetharama Palya", "Silk Institute", "Singayyanapalya",
        "Sir M. Visvesvaraya (Central College)", "South End Circle", "Sri Sathya Sai Hospital",
        "Srirampura", "Swami Vivekananda Road", "Thalaghattapura", "Trinity", "Vajarahalli",
        "Vijayanagar", "Whitefield (Kadugodi)", "Yelachenahalli", "Yeshwanthpur"
    )

    private val distanceRoute = listOf(
        "Whitefield (Kadugodi)", "Hopefarm Channasandra", "Kadugodi Tree Park", "Pattandur Agrahara",
        "Sri Sathya Sai Hospital", "Nallurhalli", "Kundalahalli", "Seetharam Palya", "Hoodi",
        "Garudacharpalya", "Singayyanapalya", "K.R. Pura", "Benniganahalli", "Baiyappanahalli",
        "Swami Vivekananda Road", "Indiranagar", "Halasuru", "Trinity", "M.G. Road", "Cubbon Park",
        "Vidhana Soudha", "Central College", "Majestic", "City Railway Station", "Magadi Road",
        "Hosahalli", "Vijayanagar", "Attiguppe", "Deepanjali Nagar", "Mysuru Road", "Pantharapalya",
        "Nayandahalli", "Rajarajeshwari Nagar", "Jnanabharathi", "Pattanagere", "Kengeri Bus Terminal",
        "Kengeri", "Challaghatta"
    )

    private var displayStations = stations.toMutableList()
    private lateinit var stationAdapter: StationAdapter

    private fun getDistanceSortIndex(station: String): Int {
        val mappedName = when (station) {
            "Dr. B.R. Ambedkar (Vidhana Soudha)" -> "Vidhana Soudha"
            "Sir M. Visvesvaraya (Central College)" -> "Central College"
            "Nadaprabhu Kempegowda (Majestic)" -> "Majestic"
            "Mysore Road" -> "Mysuru Road"
            "KR Puram" -> "K.R. Pura"
            "Seetharama Palya" -> "Seetharam Palya"
            else -> station
        }
        val index = distanceRoute.indexOf(mappedName)
        return if (index != -1) index else Int.MAX_VALUE
    }

    private fun sortStations(byDistance: Boolean) {
        if (byDistance) {
            displayStations.sortBy { it } // Secondary sort alphabetically
            displayStations.sortBy { getDistanceSortIndex(it) } // Primary sort by distance
        } else {
            displayStations.sortBy { it }
        }
        stationAdapter.updateData(displayStations.toList())
    }

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
        
        stationAdapter = StationAdapter(displayStations.toList()) { selectedStation ->
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SELECTED_STATION, selectedStation)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        recyclerView.adapter = stationAdapter

        val layoutSort = findViewById<android.widget.LinearLayout>(R.id.layoutSort)
        layoutSort.setOnClickListener { view ->
            val popup = android.widget.PopupMenu(this, view)
            popup.menu.add(0, 1, 0, "Alphabetic")
            popup.menu.add(0, 2, 1, "Distance")
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        sortStations(false)
                        true
                    }
                    2 -> {
                        sortStations(true)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}
