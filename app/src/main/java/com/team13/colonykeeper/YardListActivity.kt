package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class YardListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityYardListBinding
    private lateinit var hiveIntent: Intent
    private lateinit var yardIntent: Intent

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYardListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //start up recycler view, pass it live data of yard

        /*
        Saving myAdapter variable for lambda capture then assigning to grid view for display
        Cannot use grid view adapter directly in lambda, so need both variable here and assignment
         */
        val yardAdapter: YardAdapter = YardAdapter(applicationContext, 3)
        binding.yardGridRecyclerView.adapter = yardAdapter
        //observe the actual database info and assign it to gridview list via lambda
        colonyViewModel.allYards()
            .observe(this) {
            yards ->
            yardAdapter.addYardList(yards)
            yardAdapter.notifyDataSetChanged()
        }

        binding.yardGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.addYard.setOnClickListener{
            startActivity(Intent(this, AddBeeYardActivity::class.java))
        }
    }
}