package com.example.nammametrocloneapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.android.material.appbar.MaterialToolbar

class TimeTableActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_time_table)

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

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true

        val htmlData = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 16px;
                    }
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-bottom: 24px;
                        border: 1px solid #E0E0E0;
                    }
                    th, td {
                        border: 1px solid #E0E0E0;
                        padding: 12px;
                        text-align: center;
                        font-size: 14px;
                    }
                    th {
                        background-color: #F5F5F5;
                        font-weight: bold;
                    }
                    .header-row {
                        background-color: #F5F5F5;
                        font-weight: bold;
                        font-size: 16px;
                    }
                    .station-row {
                        font-weight: bold;
                        font-size: 16px;
                    }
                </style>
            </head>
            <body>
                <table>
                    <tr><td colspan="3" class="header-row">Monday</td></tr>
                    <tr><td colspan="3" class="station-row">From Whitefield</td></tr>
                    <tr><th>From</th><th>To</th><th>Frequency</th></tr>
                    <tr><td>04:15</td><td>04:35</td><td>20 mins</td></tr>
                    <tr><td>04:35</td><td>05:00</td><td>13 mins</td></tr>
                    <tr><td>05:00</td><td>10:57</td><td>10 mins</td></tr>
                    <tr><td>10:57</td><td>15:21</td><td>8 mins</td></tr>
                    <tr><td>15:21</td><td>22:01</td><td>10 mins</td></tr>
                    <tr><td>22:01</td><td>22:45</td><td>15 mins</td></tr>
                    <tr><td colspan="3" style="height:20px;"></td></tr>
                </table>

                <table>
                    <tr><td colspan="3" class="station-row">From Challghatta</td></tr>
                    <tr><th>From</th><th>To</th><th>Frequency</th></tr>
                    <tr><td>04:15</td><td>04:35</td><td>20 mins</td></tr>
                    <tr><td>04:35</td><td>05:15</td><td>15 mins</td></tr>
                    <tr><td>05:15</td><td>06:54</td><td>11 mins</td></tr>
                    <tr><td>06:54</td><td>12:20</td><td>10 mins</td></tr>
                    <tr><td>12:20</td><td>16:45</td><td>8 mins</td></tr>
                    <tr><td>16:45</td><td>23:05</td><td>10 mins</td></tr>
                </table>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
    }
}
