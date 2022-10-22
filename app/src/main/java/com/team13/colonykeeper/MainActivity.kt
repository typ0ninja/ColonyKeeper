package com.team13.colonykeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.team13.colonykeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var yardIntent: Intent
    private lateinit var hiveIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startYards.setOnClickListener{
            launchYard()
        }

        binding.startHives.setOnClickListener{
            launchHive()
        }
    }

    private fun launchYard(){
        yardIntent = Intent(this, YardActivity::class.java)
        startActivity(yardIntent)
    }

    private fun launchHive(){
        hiveIntent = Intent(this, HiveActivity::class.java)
        startActivity(hiveIntent)
    }
}