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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HumedadActivity : AppCompatActivity() {

    private lateinit var buttonActualizar: Button
    private lateinit var txtViewHumedadActual: TextView
    private lateinit var txtViewTextoRango : TextView
    private lateinit var txtViewAlerta : TextView
    private lateinit var switchControlHumedad : Switch

    private lateinit var database: DatabaseReference

    private var limInferior : Double = 0.0
    private var limSuperior : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_humedad)

        buttonActualizar = findViewById(R.id.buttonActualizarHumedad)
        txtViewHumedadActual = findViewById(R.id.textViewValorHumedad)
        txtViewTextoRango = findViewById(R.id.item_detail)
        txtViewAlerta = findViewById(R.id.textViewAlertaHumedad)
        switchControlHumedad = findViewById(R.id.switchRiego)

        txtViewHumedadActual.text = "-%"

        leerRangos()

        buttonActualizar.setOnClickListener(){
            readData("Humedad")
        }

        switchControlHumedad.setOnClickListener(){
            control()
        }
    }

    private fun readData(sensor: String){

        database = FirebaseDatabase.getInstance().getReference("Sensores")
        database.child(sensor).get().addOnSuccessListener {

            if (it.exists()){
                val valorHumedad = it.value
                txtViewHumedadActual.text = valorHumedad.toString() + "%"
                txtViewHumedadActual.setTextColor(ContextCompat.getColor(baseContext,R.color.black))
                txtViewAlerta.text = ""
                verificarRango(valorHumedad.toString().toDouble(),sensor)
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

        database = FirebaseDatabase.getInstance().getReference("RangosSensores/Humedad")
        database.child("Inferior").get().addOnSuccessListener {

            if (it.exists()){
                limInferior = it.value.toString().toDouble()
                txtViewTextoRango.text = txtViewTextoRango.text.toString() + limInferior.toString() + "% - "
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
                txtViewTextoRango.text = txtViewTextoRango.text.toString() + limSuperior.toString() + "%"
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
            txtViewHumedadActual.setTextColor(ContextCompat.getColor(baseContext,R.color.rojoAlerta))
            txtViewAlerta.text = "¡La humedad está fuera de rango!"
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(750)
        }
    }

    private fun control(){
        database = FirebaseDatabase.getInstance().getReference("Activadores")

        if(switchControlHumedad.isChecked){
            database.child("Humedad").setValue(true).addOnFailureListener(){
                Toast.makeText(baseContext,"Error en la activación.",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            database.child("Humedad").setValue(false)
        }
    }
}