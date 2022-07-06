package com.gv.videoteca

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


private val sharedPrefFile = "kotlinsharedpreference"
private lateinit var dbRef : DatabaseReference

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var cerca = findViewById(R.id.cercaFilm) as Button
        var novita = findViewById(R.id.novita) as Button
        var prestiti = findViewById(R.id.prestiti) as Button
        var admin = findViewById(R.id.admin) as Button
        var cercaFilm = findViewById(R.id.visualizzaFilm) as Button
        var back = findViewById(R.id.back) as Button

        back.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        cerca.setOnClickListener {
            startActivity(Intent(this,cercaFilmActivity::class.java))
            finish()
        }

        novita.setOnClickListener {
            val intent=Intent(this,scrollSelection::class.java)
            intent.putExtra("request", "news")
            startActivity(intent)
            finish()
        }

        prestiti.setOnClickListener {
            val intent=Intent(this,scrollSelection::class.java)
            intent.putExtra("request", "loans")
            startActivity(intent)
            finish()
        }

        cercaFilm.setOnClickListener {
            val intent=Intent(this,scrollSelection::class.java)
            intent.putExtra("request", "all")
            startActivity(intent)
            finish()
        }

        admin.setOnClickListener {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val user = FirebaseAuth.getInstance().currentUser
            val email = user?.email.toString()
            if (email != null) {
                val searchEmail= email.replace(".","*")

                dbRef= FirebaseDatabase.getInstance().reference.child("adminUser")

                dbRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(snapshot.exists()){
                            for (userSnap in snapshot.children){
                                val user = userSnap.getValue(User::class.java)!!
                                Log.d("searchEmail", searchEmail)
                                Log.d("useremail", user.email)
                                Log.d("uservalue", user.value.toString())

                                if ((user.email == searchEmail) && user.value){
                                    Toast.makeText(this@HomeActivity, "Admin verificato", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@HomeActivity,adminActivity::class.java))
                                    finish()
                                }
                            }
                        }

                        Toast.makeText(this@HomeActivity, "Admin non verificato", Toast.LENGTH_SHORT).show()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else{
                Toast.makeText(this, "Autenticati prima di proseguire", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }

        }

    }
}