package com.team13.colonykeeper

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.databinding.ActivityScheduleInspectionBinding
import com.team13.colonykeeper.model.PlanInspectionViewModel
import com.team13.colonykeeper.workers.InspectionNotificationWorker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        binding.setReminderCheckBox.isChecked = true
        viewModel.getWeekForecast()
        viewModel.onTimeChanged(binding.timePicker.hour, binding.timePicker.minute)

        binding.previousDayButton.setOnClickListener{viewModel.onBackArrowClicked()}
        binding.nextDayButton.setOnClickListener { viewModel.onForwardArrowClicked() }
        binding.setReminderCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onCheckBoxToggled(isChecked)
        }
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.onTimeChanged(hourOfDay, minute)
        }

        binding.planInspectionSubmitButton.setOnClickListener{
            scheduleInspection()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        Log.d("ScheduleInspection", "In onOptionsItemSelected")
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun scheduleInspection() {
        if (viewModel.isLoaded()) {
            var jobName = ""
            var tagName = ""
            var isNotif = false

            if (binding.setReminderCheckBox.isChecked) {
                isNotif = true
                val myWorkRequest = OneTimeWorkRequestBuilder<InspectionNotificationWorker>()
                    .setInitialDelay(getTimeDiff(), TimeUnit.SECONDS)
                    .setInputData(
                        workDataOf(
                            "title" to "Time to Inspect Hives",
                            "message" to "Check ColonyKeeper to start inspection",
                        )
                    )
                    .build()

                WorkManager.getInstance(this).enqueue(myWorkRequest)
                jobName = myWorkRequest.id.toString()
            }

            var newInspection = Scheduled(
                jobName,
                viewModel.dayForcast.value!!.date,
                viewModel.returnTime(),
                tagName,
                ColonyApplication.instance.curYard.id,
                intent.getStringExtra("locName")!!,
                isNotif
            )
            ColonyApplication.instance.applicationScope.launch {
                ColonyApplication.instance.colonyRepository.scheduleInspection(newInspection)
            }
            finish()
        }
    }

    private fun getTimeDiff(): Long {
        val todayDateTime = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        val scheduledDateTime = format.parse(viewModel.dayForcast.value!!.date + "T" + viewModel.returnTime() + "Z")
        val cal = Calendar.getInstance()
        cal.time = scheduledDateTime
        return (cal.timeInMillis/1000L) - (todayDateTime.timeInMillis/1000L)
    }

}