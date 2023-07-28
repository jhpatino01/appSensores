package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth //Se agrega la dependencia
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var buttonIngresar : Button //Creo la variable luego del inicio del layout FUERA DE ONCREATE
    lateinit var txtEmail : TextView
    lateinit var txtContrasena : TextView
    lateinit var txtBotonRegistrarse: TextView
    lateinit var txtBtnRecuperar : TextView


    //Crear variables de Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonIngresar = findViewById(R.id.buttonIngresar)  //Asigno el boton del layout a la variable
        txtEmail = findViewById(R.id.editTextEmail)
        txtContrasena = findViewById(R.id.editTextContrasena)
        txtBotonRegistrarse = findViewById(R.id.textViewBotonRegistrarse)
        txtBtnRecuperar = findViewById(R.id.textViewBotonRecuperar)

        firebaseAuth = Firebase.auth

        //Se presiona el boton ingresar
        buttonIngresar.setOnClickListener {
            //Log.d("BotonIngresar","Se ha presionado el boton ingresar")//Mensaje en el Logcat cuando se presiona el boton

            //Llamar funcion SignIn
            signIn(txtEmail.text.toString(),txtContrasena.text.toString()) //Pasar el texto dentro como parámetros
        }

        txtBotonRegistrarse.setOnClickListener(){
            val i = Intent(this,RegistrarUsuarioActivity::class.java)
            startActivity(i)
        }

        txtBtnRecuperar.setOnClickListener(){
            var i = Intent(this,RecuperarContrasenaActivity::class.java)
            startActivity(i)
        }
        }

    //Validar la informacion en los EditText
    private fun signIn(email: String, password: String){
        //Funcion de firebase para acceder con email y password
        //El addOnComplete
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            //Validar si la tarea se ejecutó con exito
            if(task.isSuccessful){
                val user = firebaseAuth.currentUser //Guardar el usuario

                val confirmarVerificacion = user?.isEmailVerified //Verifica si el correo ha sido verificado

                if(confirmarVerificacion==true) {
                    //IR A LA SEGUNDA ACTIVITY
                    Toast.makeText(baseContext, "Autenticación exitosa. Bienvenido.",Toast.LENGTH_SHORT).show()  //Mostrar el uid
                    val i = Intent(this, MainScreen::class.java)
                    startActivity(i)
                }
                else{
                    Toast.makeText(baseContext,"Su cuenta no está habilitada. Por favor, verifique su correo.",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(baseContext,"ERROR: Usuario y/o contraseña incorrectos.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Evitar regresar con el boton atras
    override fun onBackPressed() {
        return
    }
}