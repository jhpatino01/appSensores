package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.login.Adapter.MyAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainScreen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        firebaseAuth = Firebase.auth

        tabLayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.vierPager)

        tabLayout.addTab(tabLayout.newTab().setText("Sensores"))
        tabLayout.addTab(tabLayout.newTab().setText("Notificaciones"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_config -> {
                val i = Intent(this,MainActivityConfiguracionRangos::class.java)
                startActivity(i)
            }
            R.id.menu_CerrarSesion->{
                cerrarSesion()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    //Evitar regresar con el boton atras
    override fun onBackPressed() {
        return
    }

    //
    private fun cerrarSesion(){
        firebaseAuth.signOut()
        Toast.makeText(baseContext,"Sesión finalizada.",Toast.LENGTH_SHORT).show()
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }

}