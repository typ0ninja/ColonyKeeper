package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.HiveAdapter
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityHiveListBinding

class HiveListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHiveListBinding
    private lateinit var yardIntent: Intent

    private val hiveViewModel: HiveViewModel by viewModels {
        HiveViewModelFactory((application as ColonyApplication).hiveRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityHiveListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hiveGridRecyclerView.adapter = HiveAdapter(applicationContext, 3)

        binding.hiveGridRecyclerView.setHasFixedSize(true)

        binding.hiveNameView.text = intent.getStringExtra("yardName").toString()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.editYardButton.setOnClickListener{
            editYard()
        }

        binding.addHiveButton.setOnClickListener{
            addHive()
        }

        binding.reportButton.setOnClickListener{
            makeReport()
        }

        val hiveAdapter: HiveAdapter = HiveAdapter(applicationContext, 3)
        binding.hiveGridRecyclerView.adapter = hiveAdapter

        hiveViewModel.hivesFromYard(intent.getStringExtra("yardName").toString())
            .observe(this)
        {
                hives ->
            hiveAdapter.addHiveList(hives)
            Log.d("Getting hive Size", "${hives.size}")
            hiveAdapter.notifyDataSetChanged()
        }

    }

    fun editYard(){
        startActivity(Intent(this, EditYardActivity::class.java))
    }

    fun addHive(){
        startActivity(Intent(this, AddBeeHiveActivity::class.java))
    }

    fun makeReport(){
        startActivity(Intent(this, ReportActivity::class.java))
    }
}