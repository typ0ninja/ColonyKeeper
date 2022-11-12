package com.team13.colonykeeper

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.team13.colonykeeper.adapter.FutureInspectionsParentAdapter
import com.team13.colonykeeper.adapter.YardInspection
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.databinding.ActivityViewFutureInspectionsBinding

class ViewFutureInspectionsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewFutureInspectionsBinding
    private lateinit var futureInspectionsParentAdapter: FutureInspectionsParentAdapter

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFutureInspectionsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        supportActionBar?.title = "Manage Inspections"

        setUpViews()
        doObserveWork()
    }

    private fun setUpViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        futureInspectionsParentAdapter = FutureInspectionsParentAdapter(applicationContext)
        binding.recyclerView.adapter = futureInspectionsParentAdapter
    }

    private fun doObserveWork() {
        colonyViewModel.allYards()
            .observe(this) {
                yards ->
                val packagedYardInspections = mutableListOf<YardInspection>()
                for (yard in yards) {
                    colonyViewModel.getYardScheduled(yard.id).observe(this) {
                        scheduledInspections ->
                        packagedYardInspections.add(YardInspection(yard, scheduledInspections))
                        Log.d("ViewFutureInspections", "Length of list:${packagedYardInspections}")
                        futureInspectionsParentAdapter.addData(packagedYardInspections)
                        futureInspectionsParentAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

}