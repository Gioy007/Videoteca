package com.gv.videoteca

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


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


        var request= intent.getStringExtra("request")
        val requestArray: MutableList<String> = request!!.split(";") as MutableList<String>

        when(requestArray[0]){
            "loans"->{
                loans()
            }
            else->{
                dbRef= FirebaseDatabase.getInstance().getReference("film")

                dbRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        filmList.clear()

                        if(snapshot.exists()){
                            var i=1

                            for (filmSnap in snapshot.children){
                                val filmData = filmSnap.getValue(Film::class.java)
                                val filmId = filmSnap.toString()



                                when(requestArray[0]){
                                    "src"->{
                                        requestArray.removeAt(0)
                                        for (filmCompare in requestArray){
                                            if(filmCompare.equals(filmData)){
                                                filmList.add(filmData!!)
                                            }
                                        }
                                    }
                                    "news"->{
                                        if(snapshot.childrenCount<=7){
                                            filmList.add(filmData!!)
                                        }
                                        else{
                                            if(i<=7){
                                                filmList.add(filmData!!)
                                                i++
                                            }
                                        }
                                    }
                                    else-> filmList.add(filmData!!)
                                }
                            }
                            //l'ultimo aggiunto diventa primo
                            reverseArray(filmList)

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


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }

        filmRecyclerView.visibility= View.VISIBLE
        filmLoadingData.visibility= View.GONE


    }

    private fun reverseArray(filmList: ArrayList<Film>) {
        val temp = arrayOfNulls<Film>(filmList.size)
        for (i in filmList.indices) {
            temp[filmList.size - 1 - i] = filmList[i] as Film
        }
        for (i in filmList.indices) {
            filmList[i] = temp[i] as Film
        }
    }

    private fun loans() {
        dbRef= FirebaseDatabase.getInstance().getReference("loans")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                filmList.clear()

                if(snapshot.exists()){

                    for (filmSnap in snapshot.children){
                        val filmData = filmSnap.getValue(Film::class.java)

                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            if(filmSnap.child("user").equals(user)){
                                filmList.add(filmData!!)
                            }
                        } else {
                            Toast.makeText(this@scrollSelection, "Log in before see your loans", Toast.LENGTH_SHORT).show()
                        }


                    }


                    if (filmList.isNotEmpty()){
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
                    }
                    else{
                        //todo : non funziona bene perche visualizza la schermata vuota e non ritorna alla home

                        Toast.makeText(this@scrollSelection, "Nessun prestito all'attivo", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@scrollSelection,HomeActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}