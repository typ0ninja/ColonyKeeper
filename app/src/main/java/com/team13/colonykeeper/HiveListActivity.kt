package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.HiveAdapter
import com.team13.colonykeeper.databinding.ActivityHiveListBinding

class HiveListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHiveListBinding
    private lateinit var yardIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityHiveListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hiveGridRecyclerView.adapter = HiveAdapter(applicationContext, 3)

        binding.hiveGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.backToYard.setOnClickListener{
            backToGallery()
        }

        binding.addHiveButton.setOnClickListener{
            addHive()
        }

    }
    fun backToGallery(){
        yardIntent = Intent(this, YardListActivity::class.java)
        startActivity(yardIntent)
    }
    fun addHive(){
        startActivity(Intent(this, AddBeeHiveActivity::class.java))
    }
}