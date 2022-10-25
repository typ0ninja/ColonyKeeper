package com.team13.colonykeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityHiveIndividualBinding
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class HiveIndividualActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHiveIndividualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiveIndividualBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}