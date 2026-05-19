package com.example.nammametrocloneapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class PurchasePassActivity : AppCompatActivity() {

    private var selectedPassIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_purchase_pass)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val cardPass1Day = findViewById<MaterialCardView>(R.id.cardPass1Day)
        val cardPass3Day = findViewById<MaterialCardView>(R.id.cardPass3Day)
        val cardPass5Day = findViewById<MaterialCardView>(R.id.cardPass5Day)
        
        val ivRadio1Day = findViewById<ImageView>(R.id.ivRadio1Day)
        val ivRadio3Day = findViewById<ImageView>(R.id.ivRadio3Day)
        val ivRadio5Day = findViewById<ImageView>(R.id.ivRadio5Day)
        
        val btnProceed = findViewById<MaterialButton>(R.id.btnProceed)

        fun updateSelection(index: Int) {
            selectedPassIndex = index
            ivRadio1Day.setImageResource(if (index == 0) R.drawable.ic_radio_selected else R.drawable.ic_radio_unselected)
            ivRadio3Day.setImageResource(if (index == 1) R.drawable.ic_radio_selected else R.drawable.ic_radio_unselected)
            ivRadio5Day.setImageResource(if (index == 2) R.drawable.ic_radio_selected else R.drawable.ic_radio_unselected)
        }

        cardPass1Day.setOnClickListener { updateSelection(0) }
        cardPass3Day.setOnClickListener { updateSelection(1) }
        cardPass5Day.setOnClickListener { updateSelection(2) }

        btnProceed.setOnClickListener {
            if (selectedPassIndex != -1) {
                val passName = when (selectedPassIndex) {
                    0 -> "1 Day Pass"
                    1 -> "3 Day Pass"
                    else -> "5 Day Pass"
                }
                Toast.makeText(this, "Proceeding with \$passName", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a pass", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
