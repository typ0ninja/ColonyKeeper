package com.team13.colonykeeper

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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



        colonyViewModel.getInspections()
            .observe(this) {
                setInspectionList(it)
            }

        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, previousInspections[0].photoList[0])
        binding.imageView.setImageBitmap(bitmap)

    }

    fun setInspectionList(inspections: List<Inspections>){
        previousInspections = inspections

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName
    }
}