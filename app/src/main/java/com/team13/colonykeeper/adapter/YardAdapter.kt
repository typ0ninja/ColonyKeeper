package com.team13.colonykeeper.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.data.BeeYard
import com.team13.colonykeeper.data.DataSource


class YardAdapter(
    private val context: Context?,
    private val layout: Int
): RecyclerView.Adapter<YardAdapter.YardViewHolder>() {
    val yards: MutableList<BeeYard> = DataSource.yards

    class YardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val yardPic: ImageView? = view!!.findViewById(R.id.yardView)
        val yardName: TextView? = view!!.findViewById(R.id.yardName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YardViewHolder {

        //Inflate the layout
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.yard_item, parent, false)

        return YardViewHolder(adapterLayout)
    }
    override fun getItemCount(): Int {
        //number of pigs in list to display
        return yards.size
    }

    override fun onBindViewHolder(holder: YardAdapter.YardViewHolder, position: Int) {

        //val dog: Dog = dogs[position]
        val yard: BeeYard = yards[position]
        val resources = context?.resources
        //set the actual display views to the correct view for a given pig inside a card
        holder.yardPic?.setImageResource(yard.imageResourceId)
        holder.yardName?.text = yard.name

    }
}