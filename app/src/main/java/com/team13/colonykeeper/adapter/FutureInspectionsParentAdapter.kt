package com.team13.colonykeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.Yard

class FutureInspectionsParentAdapter(val colonyViewModel: ColonyViewModel) :
RecyclerView.Adapter<FutureInspectionsParentAdapter.YardInspectionViewHolder>() {
    var yardList: List<Yard> = mutableListOf()

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
        viewHolder.locationTextView.text = yardList[position].yardName
        val childMembersAdapter = FutureInspectionsChildAdapter(listOf())
        colonyViewModel.getYardScheduled(yardList[position].id)
            .observe(this)
            itemView.hiveInspectionList.layoutManager = LinearLayoutManager(
                itemView.context, LinearLayoutManager.VERTICAL,false
            )
            itemView.hiveInspectionList.adapter = childMembersAdapter

    }

    override fun getItemCount(): Int = yardList.size

    fun addData(list: List<Yard>) {
        yardList = list
        notifyDataSetChanged()
    }
}