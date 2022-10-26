package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.database.YardListApplication
import com.team13.colonykeeper.database.YardViewModel
import com.team13.colonykeeper.database.YardViewModelFactory
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class YardListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityYardListBinding
    private lateinit var hiveIntent: Intent
    private lateinit var yardIntent: Intent

    private val yardViewModel: YardViewModel by viewModels {
        YardViewModelFactory((application as YardListApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //start up recycler view, pass it live data of yards
        binding.yardGridRecyclerView.adapter = YardAdapter(applicationContext, 3, yardViewModel.allYards())

        binding.yardGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.addYard.setOnClickListener{
            startActivity(Intent(this, AddBeeYardActivity::class.java))

        }
    }

    /**
    fun gotoHive(){
        hiveIntent = Intent(this, HiveActivity::class.java)
        //hiveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(hiveIntent)
    }
    */

}