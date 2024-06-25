package com.example.menubuttonactivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.btnNavigator);
        bottomNavigationView.setOnItemSelectedListener {
                menuItem ->
            when(menuItem.itemId){
                R.id.btnHome -> {
                    cambiarFrame(HomeFragment())
                    true
                }
                R.id.btnLista -> {
                    cambiarFrame(ListaFragment())
                    true
                }

                R.id.btndb -> {
                    cambiarFrame(DbFragment())
                    true
                }

                R.id.btnSalir ->{
                    cambiarFrame(AcercaFragment())
                    true
                }

                else -> false
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cambiarFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frmContenedor, fragment ).commit();
    }
}
