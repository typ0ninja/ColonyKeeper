package com.team13.colonykeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class YardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityYardListBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityYardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yardGridRecyclerView.adapter = YardAdapter(applicationContext, 3)

        binding.yardGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}