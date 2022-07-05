package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class scrollSelection : AppCompatActivity() {

    private lateinit var filmRecyclerView : RecyclerView
    private lateinit var filmLoadingData : TextView
    private lateinit var filmList : ArrayList<Film>
    private lateinit var loansList : ArrayList<String>
    private lateinit var dbRefL : DatabaseReference
    private lateinit var dbRefF : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_selection)

        filmRecyclerView = findViewById(R.id.rvFilm)
        filmRecyclerView.layoutManager= LinearLayoutManager(this)
        filmRecyclerView.setHasFixedSize(true)
        filmLoadingData = findViewById(R.id.filmLoadingData)

        filmList= arrayListOf<Film>()
        loansList= arrayListOf<String>()

        var request= intent.getStringExtra("request")
        val requestArray: MutableList<String> = request!!.split(";") as MutableList<String>

        when(requestArray[0]){
            "loans"->{
                loans()
            }

            "src"->{
                search(requestArray[1])
            }

            "news"->{
                news()
            }

            "all" ->{
                allFilm()
            }
        }
    }

    private fun allFilm() {
        filmRecyclerView.visibility= View.GONE
        filmLoadingData.visibility= View.VISIBLE

        dbRefL= FirebaseDatabase.getInstance().getReference("film")

        dbRefL.addValueEventListener(object : ValueEventListener{
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

    private fun news() {
        filmRecyclerView.visibility= View.GONE
        filmLoadingData.visibility= View.VISIBLE

        dbRefL= FirebaseDatabase.getInstance().getReference("film")

        dbRefL.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                filmList.clear()

                if(snapshot.exists()){
                    var i=1

                    if(snapshot.childrenCount<=6) {
                        for (filmSnap in snapshot.children) {
                            val filmData = filmSnap.getValue(Film::class.java)
                            filmList.add(filmData!!)
                        }
                    }
                    else{
                        for (filmSnap in snapshot.children){
                            if (i>= snapshot.childrenCount-6){
                                val filmData = filmSnap.getValue(Film::class.java)
                                filmList.add(filmData!!)
                            }
                            i++
                        }
                    }

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

                    filmRecyclerView.visibility= View.VISIBLE
                    filmLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun loans() {
        filmRecyclerView.visibility= View.GONE
        filmLoadingData.visibility= View.VISIBLE
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email.toString()
        dbRefL= FirebaseDatabase.getInstance().getReference("Loans")
        dbRefF= FirebaseDatabase.getInstance().getReference("film")

        dbRefL.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                filmList.clear()

                if(snapshot.exists()){
                    for (loansSnap in snapshot.children){
                        if (email.equals(loansSnap.child("email")))
                        loansList.add(loansSnap.child("name").toString())

                        dbRefF.addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    for (filmSnap in snapshot.children){
                                        val filmData = filmSnap.getValue(Film::class.java)

                                        for (loanFilm in loansList){
                                            if (loanFilm.equals(filmData!!.name)){
                                                filmList.add(filmData)
                                            }
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
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

    private fun search(searchString : String) {
        filmRecyclerView.visibility= View.GONE
        filmLoadingData.visibility= View.VISIBLE

        dbRefL= FirebaseDatabase.getInstance().getReference("film")
        println(searchString)
        var searchArray= searchString.split("*")
        searchArray = searchArray.dropLast(1)

        dbRefL.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                filmList.clear()

                if(snapshot.exists()){
                    for (filmSnap in snapshot.children){
                        val filmData = filmSnap.getValue(Film::class.java)
                        for (searchName in searchArray){
                            if (searchName in filmData!!.name){
                                filmList.add(filmData)
                            }
                        }
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

    private fun reverseArray(filmList: ArrayList<Film>) {
        val temp = arrayOfNulls<Film>(filmList.size)
        for (i in filmList.indices) {
            temp[filmList.size - 1 - i] = filmList[i] as Film
        }
        for (i in filmList.indices) {
            filmList[i] = temp[i] as Film
        }
    }
}