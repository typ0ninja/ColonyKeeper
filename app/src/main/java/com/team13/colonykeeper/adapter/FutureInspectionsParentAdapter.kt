package com.team13.colonykeeper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R

class FutureInspectionsParentAdapter(private val context: Context?) :
RecyclerView.Adapter<FutureInspectionsParentAdapter.YardInspectionViewHolder>() {
    var yardList: List<YardInspection> = mutableListOf()

    class YardInspectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationTextView: TextView
        val childRecyclerView: RecyclerView
        val expandButtonView: ImageButton
        val expandedLayout: ConstraintLayout

        init {
            locationTextView = itemView.findViewById(R.id.location_name)
            childRecyclerView = itemView.findViewById(R.id.hive_inspection_list)
            expandButtonView = itemView.findViewById(R.id.expand_button)
            expandedLayout = itemView.findViewById(R.id.expanded_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YardInspectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.yard_inspection_item,
            parent,
            false
        )

        return YardInspectionViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: YardInspectionViewHolder, position: Int) {
        viewHolder.locationTextView.text = yardList[position].yard.yardName
        val childMembersAdapter = FutureInspectionsChildAdapter(context, yardList[position].ScheduledInspections)
        viewHolder.childRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewHolder.childRecyclerView.adapter = childMembersAdapter

        viewHolder.expandButtonView.setOnClickListener { toggleExpandedView(viewHolder) }
    }

    override fun getItemCount(): Int = yardList.size

    fun addData(list: List<YardInspection>) {
        yardList = list
        notifyDataSetChanged()
    }

    private fun toggleExpandedView(viewHolder: YardInspectionViewHolder) {
        if (viewHolder.expandedLayout.visibility == View.VISIBLE) {
            viewHolder.expandedLayout.visibility = View.GONE;
            viewHolder.expandButtonView.setImageResource(R.drawable.ic_baseline_expand_more_24);
        } else {
            viewHolder.expandedLayout.visibility = View.VISIBLE;
            viewHolder.expandButtonView.setImageResource(R.drawable.ic_baseline_expand_less_24);
        }
    }
}