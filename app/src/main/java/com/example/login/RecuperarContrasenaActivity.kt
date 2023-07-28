package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarContrasenaActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener

    lateinit var btnEnviarLink : Button
    lateinit var txtEmailRecuperar : TextView
    lateinit var btnCancelar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        //
        firebaseAuth = Firebase.auth

        btnEnviarLink = findViewById(R.id.buttonEnvialLink)
        txtEmailRecuperar = findViewById(R.id.editTextEmailRecuperar)
        btnCancelar = findViewById(R.id.buttonCancelarRecuperar)

        btnEnviarLink.setOnClickListener(){
            enviarLink(txtEmailRecuperar.text.toString())
        }

        btnCancelar.setOnClickListener(){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

    }

    //Evitar regresar con el boton atras
    override fun onBackPressed() {
        return
    }

    private fun enviarLink(email: String){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                Toast.makeText(baseContext,"Link enviado con éxito.",Toast.LENGTH_SHORT).show()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(baseContext,"Error. No se ha podido enviar el link de recuperación.",Toast.LENGTH_SHORT).show()
            }
        }
    }

}