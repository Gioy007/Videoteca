package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class filmVisualizer : AppCompatActivity() {

    private lateinit var title : TextView
    private lateinit var genre : TextView
    private lateinit var year : TextView
    private lateinit var description : TextView
    private lateinit var reserve : Button
    private lateinit var restituisci : Button
    private lateinit var dbRef : DatabaseReference

    private lateinit var loanList : ArrayList<Loan>

    private lateinit var snap : DataSnapshot
    private var valore = "aaa"

    private var id = ""
    val loanData = Loan("tizio","caio")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_visualizer)
        val back = findViewById(R.id.back) as Button

        back.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        initView()
        setValues()

        reserve.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email.toString().replace('.', '*')
            val film = title.text.toString()
            var alreadyExist = false

            if(user!= null){
                dbRef= FirebaseDatabase.getInstance().getReference("Loans")

                dbRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){

                            for (loanSnap in snapshot.children){
                                val loanData = loanSnap.getValue(Loan::class.java)
                                if (loanData?.email.equals(email) and loanData?.film.equals(film)){
                                    alreadyExist = true
                                }
                            }
                            if (!alreadyExist){
                                val loan = Loan(email, film)
                                FirebaseDatabase.getInstance().getReference().child("Loans").push().setValue(loan)
                                Toast.makeText(this@filmVisualizer, "Prenotazione effettuata", Toast.LENGTH_SHORT).show()
                                //FirebaseDatabase.getInstance().getReference().child("Loans").child(email).setValue(film) altra versione
                            }else{
                                Toast.makeText(this@filmVisualizer, "Non puoi prenotarlo nuovamente!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
            else{
                Toast.makeText(this, "Log in before see your loans", Toast.LENGTH_SHORT).show()
            }
        }


        restituisci.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email.toString().replace('.', '*')
            val film = title.text.toString()
            var alreadyExist = false
            var toDelete = ""
            var count = 1
            if(user!= null){
                dbRef= FirebaseDatabase.getInstance().getReference("Loans")
                dbRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            for (loanSnap in snapshot.children){
                                val loanData = loanSnap.getValue(Loan::class.java)
                                if (loanData?.email.equals(email) and loanData?.film.equals(film)){
                                    alreadyExist = true
                                }
                                if (alreadyExist){
                                    toDelete = loanSnap.key.toString()
                                    dbRef.child(toDelete).removeValue()
                                    Toast.makeText(this@filmVisualizer, "Film rimosso correttamente", Toast.LENGTH_SHORT).show()
                                    alreadyExist = false
                                    count--
                                }
                            }
                            if (count == 1){
                                Toast.makeText(this@filmVisualizer, "Non hai prenotato questo film", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

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
        restituisci = findViewById(R.id.restituisci)
        reserve = findViewById(R.id.reserve)
    }

    private fun setValues() {
        title.text= intent.getStringExtra("filmName")
        genre.text= intent.getStringExtra("filmGenre")
        year.text= intent.getStringExtra("filmYear")//!!!!!!!!!!!!!!!
        description.text= intent.getStringExtra("filmDescription")
    }
}