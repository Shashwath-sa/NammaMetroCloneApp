package com.example.nammametrocloneapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import android.content.Context
import android.view.View
import android.widget.TextView

class ActiveTicketsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_tickets)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            val purpleColor = ContextCompat.getColor(this@ActiveTicketsActivity, R.color.purple_main)
            setBackgroundDrawable(ColorDrawable(purpleColor))
        }

        toolbar.navigationIcon?.setTint(Color.WHITE)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val time = prefs.getLong("active_ticket_time", 0)
        
        val tvNoTickets = findViewById<TextView>(R.id.tvNoTickets)
        val cardTicket = findViewById<View>(R.id.cardTicket)
        
        val currentTime = System.currentTimeMillis()
        val twentyFourHours = 24 * 60 * 60 * 1000L
        
        if (time > 0 && currentTime - time <= twentyFourHours) {
            tvNoTickets.visibility = View.GONE
            cardTicket.visibility = View.VISIBLE
            
            val tvTicketFrom = findViewById<TextView>(R.id.tvTicketFrom)
            val tvTicketTo = findViewById<TextView>(R.id.tvTicketTo)
            val tvTicketFare = findViewById<TextView>(R.id.tvTicketFare)
            val tvTicketTime = findViewById<TextView>(R.id.tvTicketTime)
            
            val fromStation = prefs.getString("active_ticket_from", "")
            val toStation = prefs.getString("active_ticket_to", "")
            val fare = prefs.getInt("active_ticket_fare", 0)
            val passengers = prefs.getInt("active_ticket_passengers", 1)
            
            tvTicketFrom.text = "From: $fromStation"
            tvTicketTo.text = "To: $toStation"
            tvTicketFare.text = "Total Fare: ₹$fare (Passengers: $passengers)"
            
            val dateFormat = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
            tvTicketTime.text = "Booked At: " + dateFormat.format(java.util.Date(time))
        } else {
            tvNoTickets.visibility = View.VISIBLE
            cardTicket.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_refresh, menu)
        val refreshItem = menu.findItem(R.id.action_refresh)
        refreshItem?.icon?.setTint(Color.WHITE)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_refresh -> {
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
