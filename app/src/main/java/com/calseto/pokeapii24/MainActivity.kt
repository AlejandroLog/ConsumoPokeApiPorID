package com.calseto.pokeapii24

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnBuscarId : Button = findViewById(R.id.btnListado)

        btnBuscarId.setOnClickListener {
            val intent = Intent(this, PokemonActivity::class.java)
            startActivity(intent)
        }
    }
}