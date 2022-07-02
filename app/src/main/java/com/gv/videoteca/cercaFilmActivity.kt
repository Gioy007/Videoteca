package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class cercaFilmActivity : AppCompatActivity() {
    var generi = arrayOf("Genere", "Animazione", "Avventura",
        "Commedia", "Drammatico", "Fantascienza", "Fantasy",
        "Giallo", "Horror", "Musical", "Storico", "Western")
    var selectedGenre = ""

    var anni = arrayOf("Anno")
    var selectedYear = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerca_film)
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

        var search = findViewById(R.id.search) as Button
        var movie = findViewById(R.id.movie) as EditText

        search.setOnClickListener {
            var t_movie= movie.text.toString()

            if(!t_movie.equals("")){
                //Todo : cercare film nel db e visualizzare i risultati
                if(selectedYear.equals("Anno") and selectedGenre.equals("Genere")){
                    //ricerca solo per nome

                    //startActivity(Intent(this, ResultActivity::class.java))
                }else if(selectedGenre.equals("Genere")){
                    //ricerca per nome e anno


                    //startActivity(Intent(this, ResultActivity::class.java))
                }else if(selectedYear.equals("Anno")){
                    //ricerca per nome e genere



                    //startActivity(Intent(this, ResultActivity::class.java))
                }else{
                    //ricerca per nome, genere e anno


                    //startActivity(Intent(this, ResultActivity::class.java))
                }
            }
            else{
                Toast.makeText(this, "Inserisci il film da cercare", Toast.LENGTH_SHORT).show()
            }
        }
    }
}