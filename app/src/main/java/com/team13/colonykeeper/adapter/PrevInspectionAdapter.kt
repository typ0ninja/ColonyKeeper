package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.database.Yard

class PrevInspectionAdapter(
private val context: Context?,
private val layout: Int
): RecyclerView.Adapter<PrevInspectionAdapter.PrevInspectViewHolder>() {

    //private val listener =
    var inspections: List<Inspections> = emptyList()
    private lateinit var hiveIntent: Intent

    class PrevInspectViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val yardPic: ImageView? = view!!.findViewById(R.id.yardView)
        val yardName: TextView? = view!!.findViewById(R.id.yardName)
        val view: View = view!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevInspectViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PrevInspectViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return inspections.size
    }


}