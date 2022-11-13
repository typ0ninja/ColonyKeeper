package com.team13.colonykeeper

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityHiveIndividualBinding
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class HiveIndividualActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHiveIndividualBinding
    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHiveIndividualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName

        colonyViewModel.getHive(ColonyApplication.instance.curHive.id).observe(this){
            hive ->
            binding.individualHiveTitle.text = hive.hiveName
        }

        binding.hiveStartInspectionButton.setOnClickListener {
            gotoNewInspection()
        }

        binding.editHiveButton.setOnClickListener {
            editHive()
        }

        binding.hiveScheduleInspectionButton.setOnClickListener{
            scheduleInspection()
        }

        binding.viewPastInspectionsButton.setOnClickListener {
            viewPastInspections()
        }
    }

    fun gotoNewInspection(){
        startActivity(Intent(this, AddInspectionActivity::class.java))
    }

    fun editHive(){
        startActivity(Intent(this, EditHiveActivity::class.java))
    }

    fun scheduleInspection(){
        startActivity(Intent(
            this, ScheduleInspectionActivity::class.java
        ).putExtra(
           "locName",
           ColonyApplication.instance.curHive.hiveName
        ))
    }

    fun viewPastInspections(){
        startActivity(Intent(this, PastInspectionActivity::class.java))
    }

}