package com.team13.colonykeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.Scheduled

class FutureInspectionsChildAdapter(var memberData: List<Scheduled>) :
    RecyclerView.Adapter<FutureInspectionsChildAdapter.ScheduledViewHolder>() {

    private var scheduledList: List<Scheduled> = mutableListOf()

    init {
        scheduledList = memberData
    }

    class ScheduledViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locationName: TextView
        var inspectionDate: TextView
        var inspectionTime: TextView
        var isNotificationCheckBox: CheckBox

        init {
            locationName = itemView.findViewById(R.id.location_name)
            inspectionDate = itemView.findViewById(R.id.inspection_date)
            inspectionTime = itemView.findViewById(R.id.inspection_time)
            isNotificationCheckBox = itemView.findViewById(R.id.is_notification_check_box)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FutureInspectionsChildAdapter.ScheduledViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.inspection_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FutureInspectionsChildAdapter.ScheduledViewHolder, position: Int) {
        holder.locationName.text = scheduledList[position].locName
        holder.inspectionDate.text = scheduledList[position].date
        holder.inspectionTime.text = scheduledList[position].time
        holder.isNotificationCheckBox.isChecked = scheduledList[position].isNotification
    }

    override fun getItemCount(): Int = scheduledList.size
}