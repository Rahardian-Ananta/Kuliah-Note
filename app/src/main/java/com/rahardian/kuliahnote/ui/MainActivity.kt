package com.rahardian.kuliahnote.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rahardian.kuliahnote.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d("KuliahNote", "onCreate start")
            setContentView(R.layout.activity_main)
            Log.d("KuliahNote", "setContentView done")

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? androidx.navigation.fragment.NavHostFragment
            Log.d("KuliahNote", "NavHostFragment found: $navHostFragment")

            val navController = navHostFragment?.navController
            Log.d("KuliahNote", "NavController: $navController")

            val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav)
            Log.d("KuliahNote", "BottomNav: $bottomNav")

            if (navController != null && bottomNav != null) {
                bottomNav.setupWithNavController(navController)
                Log.d("KuliahNote", "setupWithNavController done")
            }
        } catch (e: Exception) {
            Log.e("KuliahNote", "CRASH in onCreate", e)
            // Show error on screen
            val tv = TextView(this).apply {
                text = "Error: ${e.message}\n\n${e.stackTraceToString()}"
                textSize = 12f
                setPadding(32, 32, 32, 32)
            }
            setContentView(tv)
        }
    }
}
