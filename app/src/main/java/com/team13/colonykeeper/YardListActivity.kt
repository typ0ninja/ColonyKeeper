package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.YardViewModel
import com.team13.colonykeeper.database.YardViewModelFactory
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class YardListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityYardListBinding
    private lateinit var hiveIntent: Intent
    private lateinit var yardIntent: Intent

    private val yardViewModel: YardViewModel by viewModels {
        YardViewModelFactory((application as ColonyApplication).yardRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("YardListActivity", "Made it to YardListActivity")
        //yardViewModel.allYards()
        //start up recycler view, pass it live data of yard

        /*
        Saving myAdapter variable for lambda capture then assigning to grid view for display
        Cannot use grid view adapter directly in lambda, so need both variable here and assignment
         */
        val yardAdapter: YardAdapter = YardAdapter(applicationContext, 3)
        binding.yardGridRecyclerView.adapter = yardAdapter
        //observe the actual database info and assign it to gridview list via lambda
        yardViewModel.allYards().observe(this)
        {
            yards ->
            yardAdapter.addYardList(yards)
            Log.d("Getting Yard Size", "${yards.size}")
            yardAdapter.notifyDataSetChanged()
        }

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