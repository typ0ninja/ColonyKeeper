package com.team13.colonykeeper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.workers.InspectionNotificationWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FutureInspectionsChildAdapter(
    private val context: Context?,
    memberData: List<Scheduled>,
    private val viewModel: ColonyViewModel,
    private val parentAdapter: FutureInspectionsParentAdapter
) : RecyclerView.Adapter<FutureInspectionsChildAdapter.ScheduledViewHolder>() {

    private var scheduledList: List<Scheduled> = mutableListOf()

    init {
        scheduledList = memberData
    }

    class ScheduledViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locationName: TextView
        var inspectionDate: TextView
        var inspectionTime: TextView
        var isNotificationCheckBox: CheckBox
        var optionButton: ImageButton

        init {
            locationName = itemView.findViewById(R.id.location_name)
            inspectionDate = itemView.findViewById(R.id.inspection_date)
            inspectionTime = itemView.findViewById(R.id.inspection_time)
            isNotificationCheckBox = itemView.findViewById(R.id.is_notification_check_box)
            optionButton = itemView.findViewById(R.id.options_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FutureInspectionsChildAdapter.ScheduledViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.inspection_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ScheduledViewHolder, position: Int) {
        holder.locationName.text = scheduledList[position].locName
        holder.inspectionDate.text = scheduledList[position].date
        holder.inspectionTime.text = scheduledList[position].time
        holder.isNotificationCheckBox.isChecked = scheduledList[position].isNotification
        holder.isNotificationCheckBox.setOnClickListener{
            toggleNotification(holder.isNotificationCheckBox.isChecked, scheduledList[position])
        }
        holder.optionButton.setOnClickListener {
            var popupMenu = PopupMenu(context, holder.optionButton)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener {_ ->
                deleteScheduled(scheduledList[position])
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = scheduledList.size

    private fun toggleNotification(isCurrentlyNotif: Boolean, scheduled: Scheduled) {
        if (isCurrentlyNotif){
            WorkManager.getInstance(context!!).cancelWorkById(UUID.fromString(scheduled.name))
            viewModel.updateInspection(
                "",
                false,
                scheduled.id
            )
            parentAdapter.notifyDataSetChanged()
        } else {
            val myWorkRequest = OneTimeWorkRequestBuilder<InspectionNotificationWorker>()
                .setInitialDelay(getTimeDiff(scheduled.date + "T" + scheduled.time + "Z"), TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Time to Inspect Hives",
                        "message" to "Check ColonyKeeper to start inspection",
                    )
                )
                .build()

            WorkManager.getInstance(context!!).enqueue(myWorkRequest)
            viewModel.updateInspection(
                myWorkRequest.id.toString(),
                true,
                scheduled.id
            )
            parentAdapter.notifyDataSetChanged()
        }
    }

    private fun getTimeDiff(dateTime: String): Long {
        val todayDateTime = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        val scheduledDateTime = format.parse(dateTime)
        val cal = Calendar.getInstance()
        cal.time = scheduledDateTime
        return (cal.timeInMillis/1000L) - (todayDateTime.timeInMillis/1000L)
    }

    private fun deleteScheduled(scheduled: Scheduled) {
        if (scheduled.isNotification){
            WorkManager.getInstance(context!!).cancelWorkById(UUID.fromString(scheduled.name))
        }
        viewModel.deleteScheduled(scheduled.id)
        parentAdapter.notifyDataSetChanged()
    }
}