package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.HiveAdapter
import com.team13.colonykeeper.data.DataSource.hives
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ColonyApplication.instance.curYard.yardName

        binding.hiveGridRecyclerView.adapter = HiveAdapter(applicationContext, 3)

        binding.hiveGridRecyclerView.setHasFixedSize(true)

        binding.hiveNameView.text = ColonyApplication.instance.curYard.yardName
        hiveViewModel.hivesFromYard(ColonyApplication.instance.curYard.id).observe(this) {
                hives ->
        }

        binding.editYardButton.setOnClickListener{
            editYard()
        }

        binding.addHiveButton.setOnClickListener{
            addHive()
        }

        binding.reportButton.setOnClickListener{
            makeReport()
        }

        /*
        Saving myAdapter variable for lambda capture then assigning to grid view for display
        Cannot use grid view adapter directly in lambda, so need both variable here and assignment
         */
        val hiveAdapter: HiveAdapter = HiveAdapter(applicationContext, 3)
        binding.hiveGridRecyclerView.adapter = hiveAdapter

        hiveViewModel.hivesFromYard(ColonyApplication.instance.curYard.id).observe(this) {
                hives ->
            Log.d("hiveAdapter", "currHiveId global${ColonyApplication.instance.curYard.id}")
            Log.d("hiveAdapter", "Hives size: ${hives.size}")
            hiveAdapter.addHiveList(hives)
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