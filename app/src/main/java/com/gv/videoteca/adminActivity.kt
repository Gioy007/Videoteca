package com.gv.videoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

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
            var t_movie = movie.text.toString()
            var t_descriprion  = description.text.toString()
            if (!t_movie.equals("") and !(selectedYear.equals("Year")) and !selectedGenre.equals("Genre") and !t_descriprion.equals("")) {
                //Todo : inserire film nel db
                Toast.makeText(this, "dati validi", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "dati non validi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}