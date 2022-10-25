package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.databinding.ActivityAddBeeYardBinding
import com.team13.colonykeeper.databinding.ActivityYardListBinding

class YardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityYardListBinding
    private lateinit var hiveIntent: Intent
    private lateinit var yardIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yardGridRecyclerView.adapter = YardAdapter(applicationContext, 3)

        binding.yardGridRecyclerView.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.addYard.setOnClickListener{
            startActivity(Intent(this, ActivityAddBeeYardBinding::class.java))

        }
    }

    /**
    fun gotoHive(){
        hiveIntent = Intent(this, HiveActivity::class.java)
        //hiveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(hiveIntent)
    }
    */

}