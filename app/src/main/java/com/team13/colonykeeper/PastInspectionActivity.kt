package com.team13.colonykeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityPastInspectionBinding


class PastInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPastInspectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}