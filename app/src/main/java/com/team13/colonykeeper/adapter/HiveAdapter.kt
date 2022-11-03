package com.team13.colonykeeper.adapter
import com.team13.colonykeeper.data.BeeHive
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.team13.colonykeeper.HiveIndividualActivity
import com.team13.colonykeeper.HiveListActivity
import com.team13.colonykeeper.R
import com.team13.colonykeeper.data.DataSource
import com.team13.colonykeeper.data.DataSource.hives
import com.team13.colonykeeper.data.DataSource.yards
import com.team13.colonykeeper.database.Hive
import com.team13.colonykeeper.database.Yard


class HiveAdapter(
    private val context: Context?,
    private val layout: Int
): RecyclerView.Adapter<HiveAdapter.HiveViewHolder>() {
    var hives: List<Hive> = emptyList()
    private lateinit var hiveIntent: Intent

    class HiveViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val hivePic: ImageView? = view!!.findViewById(R.id.hiveView)
        val hiveName: TextView? = view!!.findViewById(R.id.hiveName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiveAdapter.HiveViewHolder {

        //Inflate the layout
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.hive_item, parent, false)

        return HiveAdapter.HiveViewHolder(adapterLayout)
    }
    override fun getItemCount(): Int {
        //number of pigs in list to display
        return hives.size
    }

    override fun onBindViewHolder(holder: HiveAdapter.HiveViewHolder, position: Int) {

        //val dog: Dog = dogs[position]
        val hive: Hive = hives[position]
        val resources = context?.resources
        //set the actual display views to the correct view for a given pig inside a card
        //holder.hivePic?.setImageResource(hive.imageResourceId)
        holder.hiveName?.text = hive.hiveName
        holder.hivePic?.setOnClickListener {
            gotoHive(holder.hivePic.context)
        }
    }

    fun gotoHive(actContext: Context){
        hiveIntent = Intent(actContext, HiveIndividualActivity::class.java)
        hiveIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        actContext?.startActivity(hiveIntent)
    }

    fun addHiveList(hives: List<Hive>) {
        this.hives = hives
        Log.d("TestFunction", "${this.hives.toString()}")
        Log.d("TestFunction", "${yards.toString()}")

    }

}