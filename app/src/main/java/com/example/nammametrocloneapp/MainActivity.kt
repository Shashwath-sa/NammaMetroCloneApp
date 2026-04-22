package com.example.nammametrocloneapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFare = findViewById<Button>(R.id.btnFare)

        btnFare.setOnClickListener {
            val intent = Intent(this, FareInfo::class.java)
            startActivity(intent)
        }
    }
}