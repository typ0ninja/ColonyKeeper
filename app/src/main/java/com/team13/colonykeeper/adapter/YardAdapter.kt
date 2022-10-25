package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.team13.colonykeeper.HiveActivity
import com.team13.colonykeeper.R
import com.team13.colonykeeper.data.BeeYard
import com.team13.colonykeeper.data.DataSource
import com.team13.colonykeeper.data.DataSource.yards
import com.team13.colonykeeper.YardActivity


class YardAdapter(
    private val context: Context?,
    private val layout: Int

): RecyclerView.Adapter<YardAdapter.YardViewHolder>() {

    //private val listener =
    val yards: MutableList<BeeYard> = DataSource.yards
    private lateinit var hiveIntent: Intent

    class YardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val yardPic: ImageButton? = view!!.findViewById(R.id.yardView)
        val yardName: TextView? = view!!.findViewById(R.id.yardName)
        val view: View = view!!
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
        holder.yardPic?.setOnClickListener {
            gotoYard(holder.yardPic.context)
        }
    }

    fun gotoYard(actContext: Context){
        if(context === actContext){
            Log.d("banana", "contexts are the same")
        }
        hiveIntent = Intent(actContext, HiveActivity::class.java)
        hiveIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        actContext?.startActivity(hiveIntent)
    }



}