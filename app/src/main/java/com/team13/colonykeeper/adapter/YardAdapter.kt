package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.HiveListActivity
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.Yard


class YardAdapter(
    private val context: Context?,
    private val layout: Int,
    //private val yardList : LiveData<List<Yard>>,
    //private val owner: LifecycleOwner

): RecyclerView.Adapter<YardAdapter.YardViewHolder>() {

    //private val listener =
    var yards: List<Yard> = emptyList()
    private lateinit var hiveIntent: Intent

    class YardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val yardPic: ImageView? = view!!.findViewById(R.id.yardView)
        val yardName: TextView? = view!!.findViewById(R.id.yardName)
        val view: View = view!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YardViewHolder {
        Log.d("oncreateviewholder", "oncreate started")
        //Inflate the layout
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.yard_item, parent, false)

        return YardViewHolder(adapterLayout)
    }
    override fun getItemCount(): Int {
        //number of pigs in list to display
        Log.d("Yard_Value", "yard size = ${yards.size}")
        return yards.size
    }

    override fun onBindViewHolder(holder: YardAdapter.YardViewHolder, position: Int) {
        Log.d("Yard_Value", "made it to onbindviewholders")
        //val dog: Dog = dogs[position]
        val yard: Yard = yards[position]
        val resources = context?.resources

        Log.d("URI", "Yard URI: ${yard.photoURI}")

        val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, yard.photoURI)

        holder.yardPic?.setImageBitmap(bitmap)

        //holder.yardPic?.setImageResource(R.drawable.beeyard_temp)
        holder.yardName?.text = yard.yardName
        holder.view.setOnClickListener {
            ColonyApplication.instance.curYard = yard
            Log.d("hiveAdapter", "setting yardID: ${yard.id}")
            gotoYard(holder.view.context)
        }

    }

    fun gotoYard(actContext: Context){
        hiveIntent = Intent(actContext, HiveListActivity::class.java)
        hiveIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        actContext?.startActivity(hiveIntent)
    }

    fun addYardList(yards: List<Yard>) {
        this.yards = yards
    }



}