package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*

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
        var intent = Intent(this, scrollSelection::class.java)


        var dbRef:DatabaseReference= FirebaseDatabase.getInstance().getReference("film")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    var filmsFiltered="src;"

                    for (filmSnap in snapshot.children){
                        val filmData = filmSnap.getValue(Film::class.java)
                        var fit =true


                        if(tMovie.equals(filmData?.name)){
                            if(!selectedGenre.equals("Genre")){
                                if (!selectedGenre.equals(filmData?.genre)){
                                    fit=false
                                }
                            }
                            if(!selectedYear.equals("Year")){
                                if (!selectedYear.equals(filmData?.anno)){
                                    fit=false
                                }
                            }

                        }
                        else{
                            fit=false
                        }

                        if (fit.equals(true)){
                            filmsFiltered+= filmData?.name+";"
                        }

                    }

                    intent.putExtra("request", filmsFiltered)
                    startActivity(intent)
                    finish()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}