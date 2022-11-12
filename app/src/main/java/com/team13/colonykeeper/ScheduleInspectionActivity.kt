package com.team13.colonykeeper

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.databinding.ActivityScheduleInspectionBinding
import com.team13.colonykeeper.model.PlanInspectionViewModel
import kotlinx.coroutines.launch

class ScheduleInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityScheduleInspectionBinding
    private val viewModel: PlanInspectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleInspectionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName

        binding.viewModel = viewModel
        viewModel.getWeekForecast()

        binding.previousDayButton.setOnClickListener{viewModel.onBackArrowClicked()}
        binding.nextDayButton.setOnClickListener { viewModel.onForwardArrowClicked() }
        binding.setReminderCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.onCheckBoxToggled(isChecked)
        }
        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            viewModel.onTimeChanged(hourOfDay, minute)
        }

        binding.planInspectionSubmitButton.setOnClickListener{
            scheduleInspection()
        }
    }

    private fun scheduleInspection() {
        // If this kt is used for both hive and yard, need to make logic for
        // location name
        var jobName = ""
        var tagName = ""
        var isNotif = false


        if(binding.setReminderCheckBox.isChecked){
            isNotif = true
            //Schedule job and grab name and tag
        }

        var newInspection = Scheduled(
            jobName,
            viewModel.dayForcast.value!!.date,
            viewModel.returnTime(),
            tagName,
            ColonyApplication.instance.curYard.id,
            "all",
            isNotif
        )
        ColonyApplication.instance.applicationScope.launch {
            ColonyApplication.instance.colonyRepository.scheduleInspection(newInspection)
        }
        finish()
    }
}