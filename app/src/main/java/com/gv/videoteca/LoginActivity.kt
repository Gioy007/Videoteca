package com.gv.videoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private val auth = FirebaseAuth.getInstance()

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var email = findViewById(R.id.email) as EditText
        var password = findViewById(R.id.password) as EditText
        var login = findViewById(R.id.login) as Button
        var signup = findViewById(R.id.cercaFilm) as Button

        login.setOnClickListener{
            val t_email = email.text
            val t_psw = password.text
            loginUser(t_email.toString(), t_psw.toString())
        }

        signup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }


    }

    private fun loginUser(email: String, psw: String) {
        if (email.isEmpty() || psw.isEmpty()){
            Toast.makeText(this, "Credenziali vuote", Toast.LENGTH_SHORT).show()
        }
        else if(psw.length<6){
            Toast.makeText(this, "Password troppo corta", Toast.LENGTH_SHORT).show()
        }
        else{
            auth.signInWithEmailAndPassword(email, psw).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Login completato", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}