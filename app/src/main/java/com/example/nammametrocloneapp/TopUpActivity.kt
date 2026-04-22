package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class TopUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btn50 = findViewById<MaterialButton>(R.id.btn50)
        val btn100 = findViewById<MaterialButton>(R.id.btn100)
        val btn250 = findViewById<MaterialButton>(R.id.btn250)
        val btnTopUp = findViewById<MaterialButton>(R.id.btnTopUp)

        btn50.setOnClickListener {
            etAmount.setText("50")
        }

        btn100.setOnClickListener {
            etAmount.setText("100")
        }

        btn250.setOnClickListener {
            etAmount.setText("250")
        }

        btnTopUp.setOnClickListener {
            val amountText = etAmount.text.toString().trim()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toIntOrNull()

            if (amount == null) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount < 50) {
                Toast.makeText(this, "Minimum recharge is ₹50", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount > 3000) {
                Toast.makeText(this, "Maximum recharge is ₹3000", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount % 50 != 0) {
                Toast.makeText(this, "Amount must be in multiples of 50", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Top Up of ₹$amount successful", Toast.LENGTH_SHORT).show()
        }
    }
}