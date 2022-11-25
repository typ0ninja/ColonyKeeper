package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.database.Yard

class prevInspectionAdapter(
    private val context: Context?
): RecyclerView.Adapter<prevInspectionAdapter.PrevInspectionViewHolder>() {
    var inspections: List<Inspections> = emptyList()

    class PrevInspectionViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val date: TextView? = view!!.findViewById(R.id.prevInspectDateTextView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): prevInspectionAdapter.PrevInspectionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.inspection_prev_item, parent, false)

        return prevInspectionAdapter.PrevInspectionViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: prevInspectionAdapter.PrevInspectionViewHolder, position: Int) {
        val inspection: Inspections = inspections[position]
        inspection.name
        holder.date?.text = inspection.date
    }

    override fun getItemCount(): Int {
        return inspections.size
    }

    fun addInspectionList(inspections: List<Inspections>) {
        this.inspections = inspections
    }

}