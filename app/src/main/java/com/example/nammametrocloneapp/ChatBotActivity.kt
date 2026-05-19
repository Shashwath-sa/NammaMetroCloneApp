package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

class ChatBotActivity : AppCompatActivity() {

    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView

    private val routeDistances = listOf(
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

    private val stationKeywords = routeDistances.map { it.first.lowercase(Locale.getDefault()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_bot)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerViewChat)
        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<FloatingActionButton>(R.id.btnSend)

        // Initial Greeting
        addBotMessage("Hello! I am your Namma Metro Assistant. Ask me about fares, timings, or rules!")

        btnSend.setOnClickListener {
            val userText = etMessage.text.toString().trim()
            if (userText.isNotEmpty()) {
                addUserMessage(userText)
                etMessage.text.clear()
                generateBotResponse(userText)
            }
        }
    }

    private fun addUserMessage(text: String) {
        messages.add(ChatMessage(text, true))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun addBotMessage(text: String) {
        messages.add(ChatMessage(text, false))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun generateBotResponse(query: String) {
        val q = query.lowercase(Locale.getDefault())
        
        // 1. Check for Fare/Distance Intent
        if (q.contains("fare") || q.contains("price") || q.contains("cost") || q.contains("ticket") || q.contains("distance")) {
            val foundStations = mutableListOf<String>()
            
            // Look for stations in query
            for (station in routeDistances) {
                // handle aliases
                val alias = when (station.first) {
                    "Vidhana Soudha" -> "ambedkar"
                    "Central College" -> "visvesvaraya"
                    "Majestic" -> "kempegowda"
                    "K.R. Pura" -> "kr puram"
                    else -> station.first.lowercase(Locale.getDefault())
                }
                
                if (q.contains(station.first.lowercase(Locale.getDefault())) || q.contains(alias)) {
                    foundStations.add(station.first)
                }
            }

            if (foundStations.size >= 2) {
                // We have a from and to
                val from = foundStations[0]
                val to = foundStations[1]
                val fareDetails = calculateFareDetails(from, to)
                addBotMessage("To travel from \$from to \$to:\n\$fareDetails")
                return
            } else if (foundStations.size == 1) {
                addBotMessage("You mentioned \${foundStations[0]}. Please specify the destination station to calculate the fare.")
                return
            } else {
                addBotMessage("To calculate fare, please tell me the From and To stations. (e.g., 'Fare from Majestic to Indiranagar')")
                return
            }
        }

        // 2. Timings
        if (q.contains("time") || q.contains("timing") || q.contains("open") || q.contains("close") || q.contains("hours")) {
            addBotMessage("Namma Metro operates from 5:00 AM to 11:00 PM on all days. Trains run every 5-10 minutes depending on peak hours.")
            return
        }
        
        // 3. Rules / Penalties
        if (q.contains("rule") || q.contains("penalty") || q.contains("fine") || q.contains("luggage")) {
            addBotMessage("Here are some general rules:\n- QR Passes are strictly non-transferable. Misuse attracts a ₹500 penalty.\n- Maximum luggage allowed is 15kg per passenger.\n- Eating/Drinking inside the train is prohibited.")
            return
        }

        // 4. Passes
        if (q.contains("pass") || q.contains("1 day") || q.contains("3 day")) {
            addBotMessage("We offer 1-Day (₹250), 3-Day (₹550), and 5-Day (₹850) passes. You can purchase them in the 'QR Pass' section of the app.")
            return
        }
        
        // 5. Greeting
        if (q.contains("hi") || q.contains("hello") || q.contains("hey")) {
            addBotMessage("Hello there! How can I help you with your metro journey today?")
            return
        }

        // Fallback
        addBotMessage("I am still learning! You can ask me about fares between specific stations (e.g., 'Fare from Majestic to Indiranagar'), metro timings, passes, or rules.")
    }

    private fun calculateFareDetails(from: String, to: String): String {
        val fromIndex = routeDistances.indexOfFirst { it.first == from }
        val toIndex = routeDistances.indexOfFirst { it.first == to }

        var distance = 0.0
        val start = min(fromIndex, toIndex)
        val end = max(fromIndex, toIndex)
        for (i in start until end) {
            distance += routeDistances[i].second
        }

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
        return "Distance: \$distance km\nToken Fare: ₹\$tokenFare\nSmart Card Fare: ₹\${String.format(\"%.2f\", smartCardFare)}"
    }
}
