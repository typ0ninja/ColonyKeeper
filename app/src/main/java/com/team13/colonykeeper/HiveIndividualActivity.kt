package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityHiveIndividualBinding
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class HiveIndividualActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHiveIndividualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiveIndividualBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        startActivity(Intent(this, ScheduleInspectionActivity::class.java))
    }

    fun viewPastInspections(){
        startActivity(Intent(this, PastInspectionActivity::class.java))
    }

}