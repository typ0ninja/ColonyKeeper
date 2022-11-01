package com.team13.colonykeeper

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.activity.viewModels
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var yardIntent: Intent

//    private val hiveViewModel: HiveViewModel by viewModels {
//        HiveViewModelFactory((application as HiveListApplication).repository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startYards.setOnClickListener{
            launchYard()
        }

        val animator = ObjectAnimator.ofFloat(binding.startScreenAnimation, View.ROTATION,
            -360f, 0f)
        animator.duration = 2000
        //animator.repeatCount(Animation.INFINITE)
        animator.start()

        binding.startScreenAnimation.setOnClickListener{
            animator.start()
        }
    }

    private fun launchYard(){
        yardIntent = Intent(this, YardListActivity::class.java)
        startActivity(yardIntent)
    }
}