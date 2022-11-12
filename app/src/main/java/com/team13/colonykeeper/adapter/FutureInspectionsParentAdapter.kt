package com.team13.colonykeeper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.YardInspection
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.Yard
import kotlinx.coroutines.NonDisposableHandle.parent

class FutureInspectionsParentAdapter(private val context: Context?) :
RecyclerView.Adapter<FutureInspectionsParentAdapter.YardInspectionViewHolder>() {
    var yardList: List<YardInspection> = mutableListOf()

    class YardInspectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationTextView: TextView
        val childRecyclerView: RecyclerView

        init {
            locationTextView = itemView.findViewById(R.id.location_name)
            childRecyclerView = itemView.findViewById(R.id.hive_inspection_list)
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
        val childMembersAdapter = FutureInspectionsChildAdapter(yardList[position].ScheduledInspections)
        viewHolder.childRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewHolder.childRecyclerView.adapter = childMembersAdapter
    }

    override fun getItemCount(): Int = yardList.size

    fun addData(list: List<YardInspection>) {
        Log.d("ParentAdapter", "Here in addData")
        Log.d("ParentAdapter", "List size ${list.size}")
        yardList = list
        notifyDataSetChanged()
    }
}