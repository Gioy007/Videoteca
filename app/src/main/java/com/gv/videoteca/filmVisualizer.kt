package com.gv.videoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class filmVisualizer : AppCompatActivity() {

    private lateinit var title : TextView
    private lateinit var genre : TextView
    private lateinit var year : TextView
    private lateinit var description : TextView
    private lateinit var reserve : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_visualizer)

        initView()

        setValues()

        reserve.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            // todo : fare la prenota nella root loans con il current user e l'id del film

            if(user!= null){

            }
            else{
                Toast.makeText(this, "Log in before see your loans", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        title = findViewById(R.id.title)
        genre = findViewById(R.id.genre)
        year = findViewById(R.id.year)
        description = findViewById(R.id.description)
        reserve = findViewById(R.id.reserve)
    }

    private fun setValues() {
        title.text= intent.getStringExtra("filmName")
        genre.text= intent.getStringExtra("filmGenre")
        year.text= intent.getStringExtra("filmYear")
        description.text= intent.getStringExtra("filmDescription")
    }
}