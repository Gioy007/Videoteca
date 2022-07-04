package com.gv.videoteca

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*
import java.lang.Error

private val sharedPrefFile = "kotlinsharedpreference"

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var cerca = findViewById(R.id.cercaFilm) as Button
        var novita = findViewById(R.id.novita) as Button
        var prestiti = findViewById(R.id.prestiti) as Button
        var admin = findViewById(R.id.admin) as Button

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

        admin.setOnClickListener {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val sharedEmail = sharedPreferences.getString("email","")
            if (sharedEmail != null) {
                val searchEmail= sharedEmail.replace(".","*")

                val rootRef = FirebaseDatabase.getInstance().getReference("adminUser")
                rootRef.child(searchEmail).get().addOnSuccessListener {

                    if(it.exists()){
                        //val admin=  it.child("nome...").value
                        Toast.makeText(this, "Admin verificato", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,adminActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Non sei un utente admin, accesso negato", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{ exception ->
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Autenticati prima di proseguire", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }

        }

    }
}