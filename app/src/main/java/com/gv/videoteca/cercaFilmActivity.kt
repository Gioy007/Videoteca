package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class cercaFilmActivity : AppCompatActivity() {
    var generi = arrayOf("Genre", "Animazione", "Avventura",

        "Commedia", "Drammatico", "Fantascienza", "Fantasy",
        "Giallo", "Horror", "Musical", "Storico", "Western")
    var selectedGenre = ""


    var anni = arrayOf("Year")

    var selectedYear = ""
    var filmsSelected = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerca_film)

        var search = findViewById(R.id.search) as Button
        var movie = findViewById(R.id.movie) as EditText

        val genreSpinner = findViewById<Spinner>(R.id.genere) as Spinner
        val arrayAdapterGenre = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, generi)
        genreSpinner.adapter = arrayAdapterGenre
        genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
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
                selectedYear = anni[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        search.setOnClickListener {
            var t_movie= movie.text.toString()

            searchFilm(t_movie, selectedGenre, selectedYear)
            if(!t_movie.equals("")){
                searchFilm(t_movie,selectedGenre, selectedYear)

                if(filmsSelected.isNotEmpty()){
                    var intent= Intent(this,scrollSelection::class.java)
                    intent.putExtra("toDisplay",filmsSelected.toString())
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Nessun risultato", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Inserisci il film da cercare", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchFilm(tMovie: String, selectedGenre: String, selectedYear: String) {
        //todo : finire la selection perche non funziona

        var getData = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                for (i in snapshot.children){
                    var fit =true

                    var childName=i.child("name").toString()
                    var childGenre=i.child("genre").toString()
                    var childYear=i.child("year").toString()

                    if(tMovie.equals(childName)){
                        if(!selectedGenre.equals("Genre")){
                            if (!selectedGenre.equals(childGenre)){
                                fit=false
                            }
                        }
                        if(!selectedYear.equals("Year")){
                            if (!selectedYear.equals(childYear)){
                                fit=false
                            }
                        }

                    }
                    else{
                        fit=false
                    }

                    if (fit.equals(true)){
                        val film= snapshot.getValue().toString()
                        filmsSelected.add(film)
                    }

                }
            }
        }
    }
}