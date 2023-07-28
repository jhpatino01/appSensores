package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener

    //

    lateinit var txtNombreUsuario : TextView
    lateinit var txtEmailRegistro : TextView
    lateinit var txtContrasenaRegistro : TextView
    lateinit var txtContrasenaConfirmar : TextView
    lateinit var btnRegistrarse : Button
    lateinit var btnCancelarRegistro : Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        firebaseAuth = Firebase.auth

        txtNombreUsuario = findViewById(R.id.editTextTextUsuario)
        txtEmailRegistro = findViewById(R.id.editTextEmailRegistro)
        txtContrasenaRegistro = findViewById(R.id.editTextTextContrasena)
        txtContrasenaConfirmar = findViewById(R.id.editTextTextConfirmar)
        btnRegistrarse = findViewById(R.id.buttonRegistrarse)
        btnCancelarRegistro = findViewById(R.id.buttonCancelarRegistro)

        btnRegistrarse.setOnClickListener(){
            var pass1 = txtContrasenaRegistro.text.toString()
            var pass2 = txtContrasenaConfirmar.text.toString()

            if(pass1.equals(pass2)){
                crearCuenta(txtEmailRegistro.text.toString(),txtContrasenaRegistro.text.toString())
            }
            else{
                Toast.makeText(baseContext,"Las contraseñas no coinciden.",Toast.LENGTH_SHORT).show()
                txtContrasenaRegistro.requestFocus()
                txtContrasenaConfirmar.requestFocus()
            }
        }

        btnCancelarRegistro.setOnClickListener(){
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        


    }

    //Evitar regresar con el boton atras
    override fun onBackPressed() {
        return
    }

    private fun crearCuenta(email: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                enviarEmailVerificacion()
                Toast.makeText(baseContext,"Registro Exitoso. Por favor verifique su Email.",Toast.LENGTH_SHORT).show()

                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(baseContext,"Registro Fallido. "+task.exception,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarEmailVerificacion(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                //
            }
            else{
                //
            }
        }

    }
}