package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private val auth= FirebaseAuth.getInstance()

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var email = findViewById(R.id.email) as EditText
        var password = findViewById(R.id.password) as EditText
        var signup = findViewById(R.id.cercaFilm) as Button
        val back = findViewById(R.id.back) as Button

        back.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        signup.setOnClickListener{
            val t_email = email.text
            val t_psw = password.text

            if (t_email.isEmpty() || t_psw.isEmpty()){
                Toast.makeText(this, "Credenziali vuote", Toast.LENGTH_SHORT).show()
            }
            else if(t_psw.length<6){
                Toast.makeText(this, "Password troppo corta", Toast.LENGTH_SHORT).show()
            }
            else{
                registrerUser(t_email.toString(), t_psw.toString())
            }
        }
    }

    private fun registrerUser(tEmail: String, tPsw: String) {
        auth.createUserWithEmailAndPassword(tEmail, tPsw).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(this, "Registrazione effettuata", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Registrazione fallita", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
}