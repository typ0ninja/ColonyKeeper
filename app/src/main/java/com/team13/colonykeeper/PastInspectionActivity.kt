package com.team13.colonykeeper

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.adapter.prevInspectionAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.databinding.ActivityPastInspectionBinding
import java.io.File
import java.io.FileOutputStream


class PastInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPastInspectionBinding
    private var previousInspections: List<Inspections> = emptyList()
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    var PERMISSION_CODE = 101

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
            }

    }

    fun setInspectionList(inspections: List<Inspections>){
        previousInspections = inspections
    }

}