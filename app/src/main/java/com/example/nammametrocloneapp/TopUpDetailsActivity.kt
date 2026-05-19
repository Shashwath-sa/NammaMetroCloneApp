package com.example.nammametrocloneapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class TopUpDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up_details)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            val purpleColor = ContextCompat.getColor(this@TopUpDetailsActivity, R.color.purple_main)
            setBackgroundDrawable(ColorDrawable(purpleColor))
        }

        toolbar.navigationIcon?.setTint(Color.WHITE)
        toolbar.overflowIcon?.setTint(Color.WHITE)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val cardNumber = intent.getStringExtra("CARD_NUMBER") ?: "13005138584"
        
        findViewById<TextView>(R.id.tv_card_number_display).text = cardNumber
        findViewById<TextView>(R.id.tv_card_number_bottom).text = cardNumber

        val etAmount = findViewById<EditText>(R.id.et_amount)

        findViewById<MaterialButton>(R.id.btn_50).setOnClickListener {
            etAmount.setText("50")
        }
        findViewById<MaterialButton>(R.id.btn_100).setOnClickListener {
            etAmount.setText("100")
        }
        findViewById<MaterialButton>(R.id.btn_250).setOnClickListener {
            etAmount.setText("250")
        }

        findViewById<MaterialButton>(R.id.btn_top_up).setOnClickListener {
            val amount = etAmount.text.toString()
            if (amount.isNotEmpty() && amount.toIntOrNull() != null && amount.toInt() > 0) {
                Toast.makeText(this, "Top Up Successful for ₹$amount", Toast.LENGTH_SHORT).show()
                findViewById<TextView>(R.id.tv_balance).text = "₹ $amount"
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<android.widget.ImageView>(R.id.iv_delete_card).setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Card")
                .setMessage("Are you sure you want to delete this card?")
                .setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
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
                Toast.makeText(this, "Card already added", Toast.LENGTH_SHORT).show()
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
