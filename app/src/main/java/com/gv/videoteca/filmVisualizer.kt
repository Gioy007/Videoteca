package com.gv.videoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashMap

class filmVisualizer : AppCompatActivity() {

    private lateinit var title : TextView
    private lateinit var genre : TextView
    private lateinit var year : TextView
    private lateinit var description : TextView
    private lateinit var reserve : Button
    private lateinit var dbRef : DatabaseReference
    private lateinit var loanList : ArrayList<Loan>

    private lateinit var snap : DataSnapshot
    private var valore = "aaa"

    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_visualizer)

        initView()
        setValues()

        reserve.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser

            if(user!= null){

                //for each in loans
                //se flag c'è/non c'è prenotazione passa a true, prenotazione invalida
                //else flag è ancora false{
                val loan = Loan(user.email.toString().replace('.', '*'), title.text.toString())
                FirebaseDatabase.getInstance().getReference().child("Loans").push().setValue(loan)
                Toast.makeText(this, "Film prenotato correttamente", Toast.LENGTH_SHORT).show()
                //}
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
        year.text= intent.getStringExtra("filmYear")//!!!!!!!!!!!!!!!
        description.text= intent.getStringExtra("filmDescription")
    }

    //??????
    //private fun reserve(user : String, id : String){

        //FirebaseDatabase.getInstance().getReference().child("prenotazioni").push().setValue(user, id)


   // }
}