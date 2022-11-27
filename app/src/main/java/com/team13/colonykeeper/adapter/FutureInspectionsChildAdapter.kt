package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.*
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.Scheduled
import java.io.Serializable

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
        var optionButton: ImageButton

        init {
            locationName = itemView.findViewById(R.id.location_name)
            inspectionDate = itemView.findViewById(R.id.inspection_date)
            inspectionTime = itemView.findViewById(R.id.inspection_time)
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
        holder.optionButton.setOnClickListener {
            var popupMenu = PopupMenu(context, holder.optionButton)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener {item ->
                when (item.itemId) {
                    R.id.edit -> editScheduledInspection(scheduledList[position])
                    //R.id.delete -> deleteScheduled(scheduledList[position])
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun editScheduledInspection(scheduled: Scheduled) {
        val intent = Intent(context, EditScheduledInspectionActivity::class.java)
            .putExtra("scheduled", scheduled as Serializable)
            .addFlags(FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }

    override fun getItemCount(): Int = scheduledList.size
}