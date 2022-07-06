package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class cercaFilmActivity : AppCompatActivity() {
    var generi = arrayOf("Genre", "Animazione", "Avventura","Commedia", "Drammatico", "Fantascienza", "Fantasy","Giallo", "Horror", "Musical", "Storico", "Western")
    var selectedGenre = ""
    var anni = arrayOf("Year")
    var selectedYear = ""
    private lateinit var filmsFiltered : ArrayList<Film>
    private lateinit var dbRef : DatabaseReference
    private lateinit var movie : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerca_film)

        val search = findViewById(R.id.search) as Button
        val back = findViewById(R.id.back) as Button

        back.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        movie = findViewById(R.id.movie)
        filmsFiltered = arrayListOf<Film>()

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

        var y = 2022
        while (y > 1930) {
            anni += y.toString()
            y--
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
            val t_movie= movie.text.toString()

            if (!t_movie.equals("")){

                dbRef= FirebaseDatabase.getInstance().reference.child("film")

                dbRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(snapshot.exists()){
                            for (filmSnap in snapshot.children){
                                val filmData = filmSnap.getValue(Film::class.java)
                                val fit : Boolean = correspond(filmData!!, t_movie)

                                if(fit){
                                    filmsFiltered.add(filmData)
                                }
                            }
                        }

                        val intent= Intent(this@cercaFilmActivity,scrollSelection::class.java)
                        var request="src;"
                        println(filmsFiltered)

                        for (film in filmsFiltered){
                            request+=film.name+"*"
                        }

                        intent.putExtra("request",request)
                        startActivity(intent)
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


            }
            else {
                Toast.makeText(this, "Inserisci il film da cercare", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun correspond(filmData: Film, t_movie: String): Boolean {

        if(t_movie in filmData.name){

            if(!selectedGenre.equals("Genre")){

                if (!selectedGenre.equals(filmData.genre)){
                    return false
                }
            }
            if(!selectedYear.equals("Year")){

                if (!selectedYear.equals(filmData.anno)){
                    return false
                }
            }

        }
        else{
            return false
        }

        return true
    }
}