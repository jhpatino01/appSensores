package com.example.login.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.login.*

class Sensors : Fragment() {

    lateinit var buttonTemperatura : Button
    lateinit var buttonHumedad : Button
    lateinit var buttonLuminosidad : Button
    lateinit var buttonCrecimiento : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sensors,container,false)

        buttonTemperatura = view.findViewById(R.id.buttonTemperatura)
        buttonHumedad = view.findViewById(R.id.buttonHumedad)
        buttonLuminosidad = view.findViewById(R.id.buttonLuminosidad)
        buttonCrecimiento = view.findViewById(R.id.buttonCrecimiento)

        buttonTemperatura.setOnClickListener(){
            view.context.startActivity(Intent(view.context,TemperaturaActivity::class.java))
        }
        buttonHumedad.setOnClickListener(){
            view.context.startActivity(Intent(view.context,HumedadActivity::class.java))
        }
        buttonLuminosidad.setOnClickListener(){
            view.context.startActivity(Intent(view.context,LuminosidadActivity::class.java))
        }
        buttonCrecimiento.setOnClickListener(){
            view.context.startActivity(Intent(view.context,CrecimientoActivity::class.java))
        }

        return view
    }

}