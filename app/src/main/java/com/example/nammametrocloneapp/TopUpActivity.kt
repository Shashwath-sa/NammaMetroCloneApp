package com.example.nammametrocloneapp

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class TopUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            val purpleColor = ContextCompat.getColor(this@TopUpActivity, R.color.purple_main)
            setBackgroundDrawable(ColorDrawable(purpleColor))
        }

        toolbar.navigationIcon?.setTint(Color.WHITE)
        toolbar.overflowIcon?.setTint(Color.WHITE)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top_up, menu)
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
            R.id.action_add_card -> {
                Toast.makeText(this, "Add Card clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_card_status -> {
                Toast.makeText(this, "Card Status clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
