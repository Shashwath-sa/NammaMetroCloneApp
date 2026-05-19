package com.example.nammametrocloneapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<LinearLayout>(R.id.btnMap).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnFareInfo).setOnClickListener {
            startActivity(Intent(this, FareInfoActivity::class.java))
        }

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        fun checkRegistrationAndProceed(intentToLaunch: Intent?) {
            val isDeleted = prefs.getBoolean("is_profile_deleted", false)
            if (isDeleted) {
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_register, null)
                val dialog = MaterialAlertDialogBuilder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()

                dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                    dialog.dismiss()
                    startActivity(Intent(this, MobileVerificationActivity::class.java))
                }

                dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            } else if (intentToLaunch != null) {
                startActivity(intentToLaunch)
            }
        }

        findViewById<LinearLayout>(R.id.btnTopUp)?.setOnClickListener {
            checkRegistrationAndProceed(Intent(this, TopUpActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnQrTickets).setOnClickListener {
            checkRegistrationAndProceed(Intent(this, QrTicketsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnQrPass).setOnClickListener {
            checkRegistrationAndProceed(Intent(this, QrPassActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnTimeTable).setOnClickListener {
            startActivity(Intent(this, TimeTableActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnActiveTickets)?.setOnClickListener {
            checkRegistrationAndProceed(Intent(this, ActiveTicketsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnPurchase)?.setOnClickListener {
            checkRegistrationAndProceed(Intent(this, PurchaseTicketActivity::class.java))
        }

        val profileClickListener = android.view.View.OnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<MaterialCardView>(R.id.profileIcon).setOnClickListener(profileClickListener)
        findViewById<TextView>(R.id.userName).setOnClickListener(profileClickListener)

        findViewById<LinearLayout>(R.id.btnSupport).setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_support, null)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create()
            
            dialogView.findViewById<Button>(R.id.btnOk).setOnClickListener {
                dialog.dismiss()
            }
            
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isDeleted = prefs.getBoolean("is_profile_deleted", false)
        
        val tvProfileInitial = findViewById<TextView>(R.id.tvProfileInitial)
        val userName = findViewById<TextView>(R.id.userName)
        val profileIcon = findViewById<MaterialCardView>(R.id.profileIcon)
        
        if (isDeleted) {
            tvProfileInitial.text = "U"
            tvProfileInitial.setTextColor(Color.parseColor("#6200EE"))
            userName.text = "User"
            profileIcon.setCardBackgroundColor(Color.WHITE)
        } else {
            val savedName = prefs.getString("user_name", "Rohan Olekar") ?: "Rohan Olekar"
            val initial = if (savedName.isNotEmpty()) savedName.substring(0, 1).uppercase() else "R"
            tvProfileInitial.text = initial
            tvProfileInitial.setTextColor(Color.parseColor("#6200EE"))
            userName.text = savedName
            profileIcon.setCardBackgroundColor(Color.WHITE)
        }
    }
}