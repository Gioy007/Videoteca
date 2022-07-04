package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class scrollSelection : AppCompatActivity() {

    private lateinit var filmRecyclerView : RecyclerView
    private lateinit var filmLoadingData : TextView
    private lateinit var filmList : ArrayList<Film>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_selection)

        filmRecyclerView = findViewById(R.id.rvFilm)
        filmRecyclerView.layoutManager= LinearLayoutManager(this)
        filmRecyclerView.setHasFixedSize(true)
        filmLoadingData = findViewById(R.id.filmLoadingData)

        filmList= arrayListOf<Film>()

        getFilmData()
    }

    private fun getFilmData() {
        filmRecyclerView.visibility= View.GONE
        filmLoadingData.visibility= View.VISIBLE

        dbRef= FirebaseDatabase.getInstance().getReference("film")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                filmList.clear()

                if(snapshot.exists()){
                    for (filmSnap in snapshot.children){
                        val filmData = filmSnap.getValue(Film::class.java)
                        filmList.add(filmData!!)
                    }
                    var mAdapter = filmAdapter(filmList)
                    filmRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object : filmAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(this@scrollSelection, filmVisualizer::class.java)
                            intent.putExtra("filmName", filmList[position].name)
                            intent.putExtra("filmGenre", filmList[position].genre)
                            intent.putExtra("filmYear", filmList[position].anno)
                            intent.putExtra("filmDescription", filmList[position].description)
                            startActivity(intent)
                            finish()

                        }

                    })

                    filmRecyclerView.visibility= View.VISIBLE
                    filmLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}