package com.team13.colonykeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityAddBeeYardBinding
import com.team13.colonykeeper.databinding.ActivityMainBinding


class AddBeeYardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBeeYardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBeeYardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBeeYardButton.setOnClickListener{
            submitNewYard()
        }
    }


    fun submitNewYard(){
        //startActivity(Intent(this, YardListActivity::class.java))
        finish()
    }

}