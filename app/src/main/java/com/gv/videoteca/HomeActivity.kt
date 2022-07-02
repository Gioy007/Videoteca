package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var cerca = findViewById(R.id.cercaFilm) as Button
        var novita = findViewById(R.id.novita) as Button
        var prestiti = findViewById(R.id.prestiti) as Button

        cerca.setOnClickListener {
            startActivity(Intent(this,cercaFilmActivity::class.java))
            finish()
        }

        novita.setOnClickListener {
            startActivity(Intent(this,novita::class.java))
            finish()
        }

        prestiti.setOnClickListener {
            startActivity(Intent(this,prestiti::class.java))
            finish()
        }

    }
}