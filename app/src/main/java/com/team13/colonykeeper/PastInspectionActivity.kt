package com.team13.colonykeeper

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.adapter.prevInspectionAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.databinding.ActivityPastInspectionBinding


class PastInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPastInspectionBinding
    private var previousInspections: List<Inspections> = emptyList()

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName

        val prevInspectionAdapter: prevInspectionAdapter = prevInspectionAdapter(applicationContext)
        binding.prevInspectionGridRecyclerView.adapter = prevInspectionAdapter

        colonyViewModel.getInspections()
            .observe(this) {
                Log.d("JSON", "Size: ${it.size}")
                //fill adapter
                prevInspectionAdapter.addInspectionList(it)
                prevInspectionAdapter.notifyDataSetChanged()
                //set test pic
                setInspectionList(it)
                var firstInspection = previousInspections[0]
                Log.d("JSON", firstInspection.photoList.size.toString())
                updateImage()

            }
    }

    fun setInspectionList(inspections: List<Inspections>){
        previousInspections = inspections
    }

    fun updateImage(){
        if (!previousInspections.isEmpty()) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                previousInspections[0].photoList[0].toUri()
            )
            binding.imageView.setImageBitmap(bitmap)
        }
    }
}