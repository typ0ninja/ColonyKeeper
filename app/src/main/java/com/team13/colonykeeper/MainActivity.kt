package com.team13.colonykeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.team13.colonykeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var yardIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        findViewById<>(R.layout.activity_main.star)

        binding.startYards.setOnClickListener{
            System.out.println("made it into launchYard")
            launchYard()}
    }

    private fun launchYard(){
        System.out.println("made it into launchYard")
        yardIntent = Intent(this, YardActivity::class.java)
        startActivity(yardIntent)
    }
}