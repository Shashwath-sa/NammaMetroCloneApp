package com.example.nammametrocloneapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Button
import android.widget.LinearLayout
import android.view.View
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

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

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isDeleted = prefs.getBoolean("is_profile_deleted", false)

        val userDetailsSection = findViewById<LinearLayout>(R.id.userDetailsSection)
        val btnManageUpi = findViewById<LinearLayout>(R.id.btnManageUpi)
        val spacer1 = findViewById<View>(R.id.spacer1)
        val btnDeleteProfile = findViewById<LinearLayout>(R.id.btnDeleteProfile)
        val spacer2 = findViewById<View>(R.id.spacer2)

        if (isDeleted) {
            userDetailsSection.visibility = View.GONE
            btnManageUpi.visibility = View.GONE
            spacer1.visibility = View.GONE
            btnDeleteProfile.visibility = View.GONE
            spacer2.visibility = View.GONE
        } else {
            // Populate dynamic user details
            val userName = prefs.getString("user_name", "Rohan Olekar")
            val userMobile = prefs.getString("mobile_number", "9110485983")
            val userEmail = prefs.getString("user_email", "example@email.com")
            
            findViewById<TextView>(R.id.tvUserName).text = userName
            findViewById<TextView>(R.id.tvUserMobile).text = userMobile
            findViewById<TextView>(R.id.tvUserEmail).text = userEmail
        }

        findViewById<LinearLayout>(R.id.btnDeleteProfile).setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_profile, null)
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                prefs.edit().putBoolean("is_profile_deleted", true).apply()
                dialog.dismiss()
                finish()
            }

            dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
