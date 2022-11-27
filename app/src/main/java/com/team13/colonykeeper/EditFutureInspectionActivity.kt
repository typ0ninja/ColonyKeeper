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
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.databinding.ActivityScheduleInspectionBinding
import com.team13.colonykeeper.model.EditScheduledInspectionViewModel
import com.team13.colonykeeper.model.PlanInspectionViewModel
import com.team13.colonykeeper.workers.InspectionNotificationWorker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EditFutureInspection: AppCompatActivity() {
    private lateinit var binding: ActivityEditScheduledInspectionBinding
    private val viewModel: EditScheduledInspectionViewModel by viewModels()

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleInspectionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.viewModel = viewModel

        val scheduledID = intent.getIntExtra("id", -1)
        if (scheduledID != -1) {
            colonyViewModel.getScheduled(scheduledID).observe(this){scheduled ->
                viewModel.setScheduled(scheduled)
            }
        }

        supportActionBar?.title = viewModel.scheduled.value!!.locName
        binding.setReminderCheckBox.isChecked = viewModel.scheduled.value!!.isNotification

        viewModel.onTimeChanged(binding.timePicker.hour, binding.timePicker.minute)
        binding.previousDayButton.setOnClickListener{viewModel.onBackArrowClicked()}
        binding.nextDayButton.setOnClickListener { viewModel.onForwardArrowClicked() }
        binding.setReminderCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onCheckBoxToggled(isChecked)
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.onTimeChanged(hourOfDay, minute)
        }

        binding.editScheduledInspectionSubmitButton.setOnClickListener{
            submitEdit()
        }
    }

    private fun submitEdit() {
        if (viewModel.isLoaded()) {
            if (viewModel.scheduled.value!!.isNotification) {
                WorkManager.getInstance(applicationContext)
                    .cancelWorkById(UUID.fromString(viewModel.scheduled.value!!.name))
            }

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
            colonyViewModel.scheduleInspection(newInspection)
            colonyViewModel.deleteScheduled(viewModel.scheduled.value!!.id)
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