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
        var admin = findViewById(R.id.admin) as Button

        cerca.setOnClickListener {
            startActivity(Intent(this,cercaFilmActivity::class.java))
            finish()
        }

        novita.setOnClickListener {
            startActivity(Intent(this,novitaActivity::class.java))
            finish()
        }

        prestiti.setOnClickListener {
            startActivity(Intent(this,prestitiActivity::class.java))
            finish()
        }

        admin.setOnClickListener {
            startActivity(Intent(this,adminActivity::class.java))
            finish()
        }

    }
}