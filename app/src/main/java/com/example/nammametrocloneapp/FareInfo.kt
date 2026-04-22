package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class FareInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fare_info)

        val spSource = findViewById<Spinner>(R.id.spSource)
        val spDestination = findViewById<Spinner>(R.id.spDestination)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val stations = arrayOf(
            "Nagasandra",
            "Peenya Industry",
            "Yeshwanthpur",
            "Majestic",
            "Cubbon Park",
            "MG Road",
            "Indiranagar",
            "Baiyappanahalli"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            stations
        )

        spSource.adapter = adapter
        spDestination.adapter = adapter

        btnCalculate.setOnClickListener {

            val sourceIndex = spSource.selectedItemPosition
            val destIndex = spDestination.selectedItemPosition

            if (sourceIndex == destIndex) {
                tvResult.text = "Please select different stations"
                return@setOnClickListener
            }

            val distance = abs(sourceIndex - destIndex) * 2  // assume ~2 km per station

            val fare = when {
                distance <= 2 -> 10
                distance <= 5 -> 20
                distance <= 10 -> 30
                distance <= 15 -> 40
                else -> 50
            }

            tvResult.text = "Distance: ${distance} km\nFare: ₹$fare"
        }
    }
}