package com.team13.colonykeeper

import android.Manifest
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity(){
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
        startActivity(yardIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }




}