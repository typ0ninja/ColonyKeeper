package com.team13.colonykeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        init {
//            itemView.isNotificationCheckBox.setOnClickListener {
//            }
//            itemView.optionsButton.setOnClickListener {
//
//            }
        }

        fun bind(result: Scheduled) {
//            itemView.locationName.text = result.locName
//            itemView.inspectionDate.text = result.date
//            itemView.inspectionTime.text = result.time
//            itemView.isNotificationCheckBox.checked = result.isNotification

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScheduledViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.inspection_item, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ScheduledViewHolder, position: Int) {
        holder.bind(scheduledList[position])
    }

    override fun getItemCount(): Int = scheduledList.size
}