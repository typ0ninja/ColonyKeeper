package com.team13.colonykeeper

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityScheduleInspectionBinding
import com.team13.colonykeeper.model.PlanInspectionViewModel

class ScheduleInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityScheduleInspectionBinding
    private val viewModel: PlanInspectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleInspectionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.viewModel = viewModel
        viewModel.getWeekForecast()

        binding.previousDayButton.setOnClickListener{viewModel.onBackArrowClicked()}
        binding.nextDayButton.setOnClickListener { viewModel.onForwardArrowClicked() }

        binding.planInspectionSubmitButton.setOnClickListener{
            scheduleInspection()
        }
    }

    private fun scheduleInspection() {
        // Make inspection and set to planned inspection table
        // Need day from viewModel, time from TimePicker, is notification from checkbox
        // Possibly yard name/tag, hive name/tag
        // And if notification, need work name and ID

        if(binding.setReminderCheckBox.isChecked){
            // Set reminder
        }
        finish()
    }
}