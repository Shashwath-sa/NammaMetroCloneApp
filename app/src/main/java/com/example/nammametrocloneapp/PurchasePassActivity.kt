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
                val fare = when (selectedPassIndex) {
                    0 -> "₹ 250"
                    1 -> "₹ 550"
                    else -> "₹ 850"
                }
                
                val sdf = java.text.SimpleDateFormat("dd-MM-yyyy\nHH:mm:ss", java.util.Locale.getDefault())
                val now = java.util.Calendar.getInstance()
                val validFrom = sdf.format(now.time)
                
                val daysToAdd = when (selectedPassIndex) {
                    0 -> 1
                    1 -> 3
                    else -> 5
                }
                
                val tillCalendar = java.util.Calendar.getInstance()
                tillCalendar.add(java.util.Calendar.DAY_OF_YEAR, daysToAdd)
                tillCalendar.set(java.util.Calendar.HOUR_OF_DAY, 2)
                tillCalendar.set(java.util.Calendar.MINUTE, 30)
                tillCalendar.set(java.util.Calendar.SECOND, 0)
                
                val validTill = sdf.format(tillCalendar.time)

                val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_pass, null)
                val tvPassType = dialogView.findViewById<android.widget.TextView>(R.id.tvPassType)
                val tvFare = dialogView.findViewById<android.widget.TextView>(R.id.tvFare)
                val tvValidFrom = dialogView.findViewById<android.widget.TextView>(R.id.tvValidFrom)
                val tvValidTill = dialogView.findViewById<android.widget.TextView>(R.id.tvValidTill)
                
                tvPassType.text = passName
                tvFare.text = fare
                tvValidFrom.text = validFrom
                tvValidTill.text = validTill
                
                val dialog = com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()
                    
                dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnConfirm).setOnClickListener {
                    dialog.dismiss()
                    
                    val warningDialogView = layoutInflater.inflate(R.layout.dialog_pass_warning, null)
                    val warningDialog = com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                        .setView(warningDialogView)
                        .setCancelable(false)
                        .create()
                        
                    warningDialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnOk).setOnClickListener {
                        warningDialog.dismiss()
                        Toast.makeText(this, "Pass Purchased Successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    
                    warningDialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel).setOnClickListener {
                        warningDialog.dismiss()
                    }
                    
                    warningDialog.show()
                }
                
                dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel).setOnClickListener {
                    dialog.dismiss()
                }
                
                dialog.show()
            } else {
                Toast.makeText(this, "Please select a pass", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
