package com.example.bookify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Inisialisasi tombol "Mulai"
        val startButton = findViewById<Button>(R.id.startButton)

        // Aksi ketika tombol "Mulai" diklik
        startButton.setOnClickListener { v: View? ->
            val intent =
                Intent(
                    this@StartActivity,
                    MainActivity::class.java
                )
            startActivity(intent)
        }
    }
}