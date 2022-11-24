package com.team13.colonykeeper.adapter

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R

class InspectionPicAdapter(
private val context: Context?,
private val layout: Int
): RecyclerView.Adapter<InspectionPicAdapter.InspectionPicViewHolder>() {

    //private val listener =
    var inspectionPics: List<String> = emptyList()

    class InspectionPicViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        //find each view and assign to this container
        val inspectionPic: ImageView? = view!!.findViewById(R.id.inspectPic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspectionPicViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.inspection_pic_item, parent, false)

        return InspectionPicAdapter.InspectionPicViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: InspectionPicViewHolder, position: Int) {
        val pic: String = inspectionPics[position]
        val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, pic.toUri())

        holder.inspectionPic?.setImageBitmap(bitmap)

    }

    override fun getItemCount(): Int {
        Log.d("picAdapter", "size of adapter: ${inspectionPics.size}")
        return inspectionPics.size
    }

    fun addPicList(inspectionPics: List<String>) {
        this.inspectionPics = inspectionPics
    }

}