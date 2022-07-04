package com.gv.videoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class filmVisualizer : AppCompatActivity() {

    private lateinit var title : TextView
    private lateinit var genre : TextView
    private lateinit var year : TextView
    private lateinit var description : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_visualizer)

        initView()

        setValues()
    }

    private fun initView() {
        title = findViewById(R.id.title)
        genre = findViewById(R.id.genre)
        year = findViewById(R.id.year)
        description = findViewById(R.id.description)
    }

    private fun setValues() {
        title.text= intent.getStringExtra("filmName")
        genre.text= intent.getStringExtra("filmGenre")
        year.text= intent.getStringExtra("filmYear")
        description.text= intent.getStringExtra("filmDescription")
    }
}