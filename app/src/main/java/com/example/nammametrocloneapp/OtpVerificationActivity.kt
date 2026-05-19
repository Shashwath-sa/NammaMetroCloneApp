package com.example.nammametrocloneapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class OtpVerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp_verification)

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

        val mobileNumber = intent.getStringExtra("MOBILE_NUMBER") ?: ""
        findViewById<TextView>(R.id.tvMobileNumber).text = "+91 \$mobileNumber"

        val etOtp = findViewById<EditText>(R.id.etOtp)
        val btnVerify = findViewById<MaterialButton>(R.id.btnVerify)

        btnVerify.setOnClickListener {
            val otp = etOtp.text.toString()
            if (otp.length == 6) {
                // Mock verification success
                val intent = Intent(this, RegisterProfileActivity::class.java)
                intent.putExtra("MOBILE_NUMBER", mobileNumber)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please enter a 6-digit OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
