// Purchase.kt
package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Purchase : AppCompatActivity() {

    private var count = 1
    private lateinit var txtCount: TextView

    val stations = arrayOf(
        "Whitefield",
        "MG Road",
        "Pattangere",
        "Majestic",
        "Indiranagar"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        val fromStation = findViewById<AutoCompleteTextView>(R.id.edtFrom)
        val toStation = findViewById<AutoCompleteTextView>(R.id.edtTo)

        txtCount = findViewById(R.id.txtCount)

        val btnPlus = findViewById<TextView>(R.id.btnPlus)
        val btnMinus = findViewById<TextView>(R.id.btnMinus)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, stations)

        fromStation.setAdapter(adapter)
        toStation.setAdapter(adapter)

        fromStation.setOnClickListener { fromStation.showDropDown() }
        toStation.setOnClickListener { toStation.showDropDown() }

        btnPlus.setOnClickListener {
            count++
            txtCount.text = count.toString()
        }

        btnMinus.setOnClickListener {
            if (count > 1) {
                count--
                txtCount.text = count.toString()
            }
        }
    }
}