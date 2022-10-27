package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityAddBeeHiveBinding

class AddBeeHiveActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddBeeHiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBeeHiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBeeHiveButton.setOnClickListener{
            submitNewHive()
        }
    }

    fun submitNewHive(){
        //startActivity(Intent(this, HiveListActivity::class.java))
        finish()
    }
}