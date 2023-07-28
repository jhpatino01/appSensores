package com.example.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LuminosidadActivity : AppCompatActivity() {

    private lateinit var buttonActualizar: Button
    private lateinit var txtViewLuminosidadActual: TextView
    private lateinit var txtViewTextoRango : TextView
    private lateinit var txtViewAlerta : TextView
    private lateinit var switchControlLuminosidad : Switch

    private lateinit var database: DatabaseReference

    private var limInferior : Double = 0.0
    private var limSuperior : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luminosidad)

        buttonActualizar = findViewById(R.id.buttonActualizarLuminosidad)
        txtViewLuminosidadActual = findViewById(R.id.textViewValorLuminosidad)
        txtViewTextoRango = findViewById(R.id.item_detail)
        txtViewAlerta = findViewById(R.id.textViewAlertaLuminosidad)
        switchControlLuminosidad = findViewById(R.id.switchIluminacion)

        leerRangos()

        buttonActualizar.setOnClickListener(){
            readData("Luminosidad")
        }

        switchControlLuminosidad.setOnClickListener(){
            control()
        }
    }

    private fun readData(sensor: String){

        database = FirebaseDatabase.getInstance().getReference("Sensores")
        database.child(sensor).get().addOnSuccessListener {

            if (it.exists()){
                val valorLuminosidad = it.value
                txtViewLuminosidadActual.text = valorLuminosidad.toString() + "lx"
                txtViewLuminosidadActual.setTextColor(ContextCompat.getColor(baseContext,R.color.black))
                txtViewAlerta.text = ""
                verificarRango(valorLuminosidad.toString().toDouble(),sensor)
            }
            else{
                Toast.makeText(this, "Error en la lectura.", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener(){
            Toast.makeText(this, "Error en la lectura.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun leerRangos(){

        txtViewTextoRango.text = "Rango: "

        database = FirebaseDatabase.getInstance().getReference("RangosSensores/Luminosidad")
        database.child("Inferior").get().addOnSuccessListener {

            if (it.exists()){
                limInferior = it.value.toString().toDouble()
                txtViewTextoRango.text = txtViewTextoRango.text.toString() + limInferior.toString() + "lx - "
            }
            else{
                Toast.makeText(this, "Error en la lectura (Rango Inferior).", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Error en la lectura (Rango Inferior).", Toast.LENGTH_SHORT).show()
        }

        database.child("Superior").get().addOnSuccessListener {
            if (it.exists()){
                limSuperior = it.value.toString().toDouble()
                txtViewTextoRango.text = txtViewTextoRango.text.toString() + limSuperior.toString() + "lx"
            }
            else{
                Toast.makeText(this, "Error en la lectura (Rango Superior).", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Error en la lectura (Rango Superior).", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificarRango(valor : Double, sensor : String){
        if (valor < limInferior || valor > limSuperior){
            txtViewLuminosidadActual.setTextColor(ContextCompat.getColor(baseContext,R.color.rojoAlerta))
            txtViewAlerta.text = "¡La humedad está fuera de rango!"
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(750)
        }
    }

    private fun control(){
        database = FirebaseDatabase.getInstance().getReference("Activadores")

        if(switchControlLuminosidad.isChecked){
            database.child("Luminosidad").setValue(true).addOnFailureListener(){
                Toast.makeText(baseContext,"Error en la activación.",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            database.child("Luminosidad").setValue(false)
        }
    }
}