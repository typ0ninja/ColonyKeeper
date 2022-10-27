package com.team13.colonykeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityScheduleInspectionBinding

class ScheduleInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityScheduleInspectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener{
            scheduleInspection()
        }
    }

    fun scheduleInspection() {
        finish()
    }
}