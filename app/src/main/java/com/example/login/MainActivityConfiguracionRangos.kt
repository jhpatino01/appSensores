package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivityConfiguracionRangos : AppCompatActivity() {

    private lateinit var spinnerSensores : Spinner
    private lateinit var editTextLimInferior : EditText
    private lateinit var editTextLimSuperior : EditText
    private lateinit var btnConfigurar : Button
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_configuracion_rangos)

        spinnerSensores = findViewById(R.id.spinnerSensores)
        editTextLimInferior = findViewById(R.id.editTextLimInferior)
        editTextLimSuperior = findViewById(R.id.editTextLimSuperior)
        btnConfigurar = findViewById(R.id.buttonConfRngSensor)

        val listaSensores = resources.getStringArray(R.array.sensores)

        ArrayAdapter.createFromResource(this,R.array.sensores, android.R.layout.simple_spinner_item)
            .also{adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSensores.adapter = adapter
            }

        btnConfigurar.setOnClickListener(){

            if(editTextLimInferior.length()==0 || editTextLimSuperior.length()==0){
                Toast.makeText(baseContext,"Rellene todos los campos.",Toast.LENGTH_SHORT).show()
            }
            else{
                var limInferior = editTextLimInferior.text.toString().toDouble()
                var limSuperior = editTextLimSuperior.text.toString().toDouble()
                var sensor = spinnerSensores.selectedItem.toString()

                configurarSensor(limInferior,limSuperior,sensor)
            }
        }

    }

    private fun configurarSensor(limInferior : Double, limSuperior: Double, sensor : String){

        database = FirebaseDatabase.getInstance().getReference("RangosSensores/"+sensor)
        database.child("Inferior").setValue(limInferior).addOnFailureListener(){
            Toast.makeText(baseContext,"Error en la configuración",Toast.LENGTH_SHORT).show()
        }
        database.child("Superior").setValue(limSuperior).addOnFailureListener(){
            Toast.makeText(baseContext,"Error en la configuración",Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(baseContext,"Sensor " + sensor + " configurado.",Toast.LENGTH_SHORT).show()
    }
}