package com.team13.colonykeeper

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.FutureInspectionsParentAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.databinding.ActivityViewFutureInspectionsBinding

class ViewFutureInspectionsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewFutureInspectionsBinding

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFutureInspectionsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName

        val futureInspectionsParentAdapter = FutureInspectionsParentAdapter(colonyViewModel)
        binding.recyclerView.adapter = futureInspectionsParentAdapter
        colonyViewModel.allYards()
            .observe(this) {
                yards ->
                for (yard in yards) {
                    colonyViewModel.getYardScheduled(yard.id).observe
                }
                futureInspectionsParentAdapter.addData(yards)
                futureInspectionsParentAdapter.notifyDataSetChanged()
            }
    }
}