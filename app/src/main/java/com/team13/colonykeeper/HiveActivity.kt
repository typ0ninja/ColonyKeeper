package com.team13.colonykeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.adapter.HiveAdapter
import com.team13.colonykeeper.databinding.ActivityHiveListBinding

class HiveActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHiveListBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityHiveListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hiveGridRecyclerView.adapter = HiveAdapter(applicationContext, 3)

        binding.hiveGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}