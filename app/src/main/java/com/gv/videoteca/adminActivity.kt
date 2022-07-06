package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class adminActivity : AppCompatActivity() {
    var generi = arrayOf("Genre", "Animazione", "Avventura",
        "Commedia", "Drammatico", "Fantascienza", "Fantasy",
        "Giallo", "Horror", "Musical", "Storico", "Western")
    var selectedGenre = ""

    var anni = arrayOf("Year")
    var selectedYear = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val genreSpinner = findViewById<Spinner>(R.id.genere) as Spinner
        val arrayAdapterGenre = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, generi)
        val back = findViewById(R.id.back) as Button

        back.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }


        genreSpinner.adapter = arrayAdapterGenre
        genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                Toast.makeText(applicationContext, "Selezionato " + generi[pos], Toast.LENGTH_LONG).show()
                selectedGenre = generi[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        var y = 1930
        while (y < 2023) {
            anni += y.toString()
            y++
        }
        val yearSpinner = findViewById<Spinner>(R.id.anno) as Spinner
        val arrayAdapterYear = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, anni)
        yearSpinner.adapter = arrayAdapterYear
        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                Toast.makeText(applicationContext, "Selezionato " + anni[pos], Toast.LENGTH_LONG).show()
                selectedYear = anni[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        var add = findViewById(R.id.aggiungi) as Button
        var movie = findViewById(R.id.movie) as EditText
        var description = findViewById(R.id.descrizione) as EditText

        add.setOnClickListener {
            var t_title = movie.text.toString()
            var t_descriprion  = description.text.toString()

            if (!t_title.equals("") and !(selectedYear.equals("Year")) and !selectedGenre.equals("Genre") and !t_descriprion.equals("")) {

                var film = Film(t_title, selectedGenre, selectedYear.toInt(), t_descriprion)
                registerNewFilm(film)

            } else {
                Toast.makeText(this, "Dati non validi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerNewFilm(film: Film) {
        FirebaseDatabase.getInstance().getReference().child("film").push().setValue(film)
        Toast.makeText(this, "Nuovo film inserito", Toast.LENGTH_SHORT).show()
    }
}