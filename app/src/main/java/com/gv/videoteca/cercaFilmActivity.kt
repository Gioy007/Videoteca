package com.gv.videoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class cercaFilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerca_film)

        var search = findViewById(R.id.search) as Button
        var movie = findViewById(R.id.movie) as EditText

        search.setOnClickListener {
            var t_movie= movie.text.toString()

            if(!t_movie.equals("")){
                //Todo : cercare film nel db e visualizzare i risultati
            }
            else{
                Toast.makeText(this, "Inserisci il film da cercare", Toast.LENGTH_SHORT).show()
            }
        }
    }
}