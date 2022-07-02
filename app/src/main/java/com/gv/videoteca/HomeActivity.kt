package com.gv.videoteca

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
            startActivity(Intent(this,novitaActivity::class.java))
            finish()
        }

        prestiti.setOnClickListener {
            startActivity(Intent(this,prestitiActivity::class.java))
            finish()
        }

        admin.setOnClickListener {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val sharedEmail = sharedPreferences.getString("email","")
            //sostituisco punto con asterisco

            val rootRef = FirebaseDatabase.getInstance().getReference("adminUser")
            val valuesRef= rootRef.equalTo(sharedEmail)
            //val val

            /*if(!sharedEmail.equals("")){
                Toast.makeText(this, , Toast.LENGTH_SHORT).show()
            }else{
                //outputName.setText(sharedNameValue).toString()
                //outputId.setText(sharedIdValue.toString())
            }*/

            startActivity(Intent(this,adminActivity::class.java))
        }

    }
}